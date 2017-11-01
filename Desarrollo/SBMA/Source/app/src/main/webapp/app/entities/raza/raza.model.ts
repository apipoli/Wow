import { BaseEntity } from './../../shared';

export class Raza implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
    ) {
    }
}
