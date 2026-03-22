import { post } from '@/api'

export interface CreateApplicationRequest {
	framework: string
	uploadFile: File
	appName: string
	appEnglishName: string
	visibility: string
	appDescription: string
}

function buildCreateApplicationFormData(request: CreateApplicationRequest): FormData {
	const payload = new FormData()

	payload.append('framework', request.framework)
	payload.append('file', request.uploadFile)
	payload.append('appName', request.appName)
	payload.append('appEnglishName', request.appEnglishName)
	payload.append('visibility', request.visibility)
	payload.append('appDescription', request.appDescription)

	return payload
}

export const applicationCreateNew = (request: CreateApplicationRequest) => {
	const data = buildCreateApplicationFormData(request)

	return post<void>('/application/create/new', data, {
		headers: {
			'Content-Type': 'multipart/form-data'
		},
		withCredentials: true
	})
}

