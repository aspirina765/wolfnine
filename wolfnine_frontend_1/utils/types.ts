import { CustomMessageType } from "../entities/enums"

export type MessageType = {
    title: string,
    message: string,
    type: CustomMessageType
}

export declare type Credential = {
    username: string,
    password: string
}
