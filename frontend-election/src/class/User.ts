export class User {
    constructor(
        public user_id: number,
        public username: string,
        public email: string,
        public password: string,
        public role: string,
    )
    {}
}