/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 4.1.1 on 2026-08-31 08:28:21.

export namespace ClassMateService {

    export interface CreateUserRequest {
        displayName: string;
        email: string;
        gender: Gender;
        birthdate: Date;
    }

    export interface UserProfile {
        registrationNeeded: boolean;
        authProvider: string;
        authSubject: string;
        userId: string;
        displayName: string;
        email: string;
        gender: Gender;
        birthdate: Date;
        roles: Role[];
    }

    export interface UserProfileBuilder {
    }

    export type Gender = "MAN" | "WOMAN";

    export type Role = "ROLE_STUDENT" | "ROLE_INSTRUCTOR" | "ROLE_ADMIN";

}
