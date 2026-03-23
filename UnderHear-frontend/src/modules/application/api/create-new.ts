import { post } from '@/api'

export interface CreateApplicationRequest {
	framework: string
	appFile: File
	appName: string
	appEnglishName: string
	visibility: string
	appDescription: string
}

function buildCreateApplicationFormData(request: CreateApplicationRequest): FormData {
	const payload = new FormData()

	payload.append('framework', request.framework)
	payload.append('appFile', request.appFile)
	payload.append('appName', request.appName)
	payload.append('appEnglishName', request.appEnglishName)
	payload.append('visibility', request.visibility)
	payload.append('appDescription', request.appDescription)

	return payload
}

export const applicationCreateNew = (request: CreateApplicationRequest) => {
	const data = buildCreateApplicationFormData(request)

	return post<void>('/application/create/new', data, {
		withCredentials: true
	})
}

