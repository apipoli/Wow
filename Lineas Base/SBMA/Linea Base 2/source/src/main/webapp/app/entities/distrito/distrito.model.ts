import { BaseEntity } from './../../shared';

export class Distrito implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public provincia?: BaseEntity,
    ) {
    }
}
