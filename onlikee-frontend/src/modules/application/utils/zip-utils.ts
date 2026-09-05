export async function createHtmlZip(htmlSource: string): Promise<File> {
  const { strToU8, zip } = await import('fflate')
  const archive = await new Promise<Uint8Array>((resolve, reject) => {
    zip({
      'index.html': strToU8(htmlSource)
    }, (error, data) => {
      if (error) {
        reject(error)
        return
      }
      resolve(data)
    })
  })

  return new File([Uint8Array.from(archive)], 'dist.zip', {
    type: 'application/zip'
  })
}
