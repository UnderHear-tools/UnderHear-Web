interface CollectUploadFilesOptions {
  accept?: string
  directory?: boolean
}

const DIRECTORY_TRAVERSAL_CONCURRENCY = 8

type WebkitDataTransferItem = DataTransferItem & {
  webkitGetAsEntry: () => FileSystemEntry | null
}

export function collectFilesFromInput(
  fileList: FileList | null,
  options: CollectUploadFilesOptions = {}
): File[] {
  const files = fileList ? Array.from(fileList) : []
  return normalizeUploadFiles(files, options)
}

export async function collectFilesFromDrop(
  dataTransfer: DataTransfer | null,
  options: CollectUploadFilesOptions = {}
): Promise<File[]> {
  if (!dataTransfer) {
    return []
  }

  const files = options.directory
    ? await collectDirectoryDropFiles(dataTransfer)
    : Array.from(dataTransfer.files ?? [])

  return normalizeUploadFiles(files, options)
}

function normalizeUploadFiles(
  files: File[],
  options: CollectUploadFilesOptions
): File[] {
  const selectedFiles = options.directory ? files : files.slice(0, 1)
  return selectedFiles.filter(file => isAccepted(file, options.accept))
}

async function collectDirectoryDropFiles(dataTransfer: DataTransfer): Promise<File[]> {
  const entryFiles = await collectFilesFromItems(dataTransfer.items)
  if (entryFiles.length > 0) {
    return entryFiles
  }

  return Array.from(dataTransfer.files ?? [])
}

async function collectFilesFromItems(items: DataTransferItemList | undefined): Promise<File[]> {
  if (!items || items.length === 0) {
    return []
  }

  const fileItems = Array.from(items).filter(item => item.kind === 'file')
  const fileGroups = await mapWithConcurrency(
    fileItems,
    DIRECTORY_TRAVERSAL_CONCURRENCY,
    async item => {
      const webkitItem = item as WebkitDataTransferItem
      const entry = webkitItem.webkitGetAsEntry?.()
      if (entry) {
        return collectFilesFromEntry(entry)
      }

      const file = item.getAsFile()
      return file ? [file] : []
    }
  )

  return flattenFileGroups(fileGroups)
}

async function collectFilesFromEntry(entry: FileSystemEntry): Promise<File[]> {
  if (entry.isFile) {
    return [await readFileEntry(entry as FileSystemFileEntry)]
  }

  if (!entry.isDirectory) {
    return []
  }

  const childEntries = await readDirectoryEntries(entry as FileSystemDirectoryEntry)
  const fileGroups = await mapWithConcurrency(
    childEntries,
    DIRECTORY_TRAVERSAL_CONCURRENCY,
    childEntry => collectFilesFromEntry(childEntry)
  )

  return flattenFileGroups(fileGroups)
}

function readFileEntry(entry: FileSystemFileEntry): Promise<File> {
  return new Promise((resolve, reject) => {
    entry.file(resolve, reject)
  })
}

async function readDirectoryEntries(
  entry: FileSystemDirectoryEntry
): Promise<FileSystemEntry[]> {
  const reader = entry.createReader()
  const entries: FileSystemEntry[] = []

  while (true) {
    const chunk = await readDirectoryEntriesChunk(reader)
    if (chunk.length === 0) {
      return entries
    }

    entries.push(...chunk)
  }
}

function readDirectoryEntriesChunk(
  reader: FileSystemDirectoryReader
): Promise<FileSystemEntry[]> {
  return new Promise((resolve, reject) => {
    reader.readEntries(resolve, reject)
  })
}

async function mapWithConcurrency<T, R>(
  items: readonly T[],
  limit: number,
  mapper: (item: T, index: number) => Promise<R>
): Promise<R[]> {
  if (items.length === 0) {
    return []
  }

  const concurrency = Math.max(1, Math.min(limit, items.length))
  const results = new Array<R>(items.length)
  let nextIndex = 0

  async function worker() {
    while (nextIndex < items.length) {
      const currentIndex = nextIndex
      nextIndex += 1
      results[currentIndex] = await mapper(items[currentIndex], currentIndex)
    }
  }

  await Promise.all(Array.from({ length: concurrency }, () => worker()))
  return results
}

function flattenFileGroups(fileGroups: File[][]): File[] {
  const files: File[] = []
  for (const fileGroup of fileGroups) {
    files.push(...fileGroup)
  }

  return files
}

function isAccepted(file: File, accept = ''): boolean {
  const trimmedAccept = accept.trim()
  if (!trimmedAccept) {
    return true
  }

  const acceptedTypes = trimmedAccept
    .split(',')
    .map(type => type.trim())
    .filter(Boolean)

  return acceptedTypes.some(type => matchesAcceptType(file, type))
}

function matchesAcceptType(file: File, acceptType: string): boolean {
  const normalizedType = acceptType.toLowerCase()
  const fileType = file.type.toLowerCase()
  const fileName = file.name.toLowerCase()

  if (normalizedType.startsWith('.')) {
    return fileName.endsWith(normalizedType)
  }

  if (normalizedType.endsWith('/*')) {
    return fileType.startsWith(normalizedType.slice(0, -1))
  }

  return fileType === normalizedType
}
