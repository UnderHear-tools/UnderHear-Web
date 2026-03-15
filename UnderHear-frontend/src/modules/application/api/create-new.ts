import { post } from '@/api'

export type ApplicationFramework = 'html' | 'vue' | 'react'

export interface ApplicationFormData {
	appName: string
	englishName: string
	visibility: string
	appDescription: string
}

export interface CreateApplicationRequest {
	framework: ApplicationFramework
	file: File | null
	fileSource: File | null
	formData: ApplicationFormData
}

function resolveUploadFile(request: CreateApplicationRequest): File {
	const uploadFile = request.file ?? request.fileSource

	if (!uploadFile) {
		throw new Error('未找到可上传的文件。')
	}

	return uploadFile
}

function buildCreateApplicationFormData(request: CreateApplicationRequest): FormData {
	const payload = new FormData()
	const uploadFile = resolveUploadFile(request)

	payload.append('framework', request.framework)
	payload.append('file', uploadFile)
	payload.append('appName', request.formData.appName)
	payload.append('englishName', request.formData.englishName)
	payload.append('visibility', request.formData.visibility)
	payload.append('appDescription', request.formData.appDescription)

	return payload
}

export const createApplication = (request: CreateApplicationRequest) => {
	const data = buildCreateApplicationFormData(request)

	return post<void>('/application/create', data, {
		headers: {
			'Content-Type': 'multipart/form-data'
		},
		withCredentials: true
	})
}

