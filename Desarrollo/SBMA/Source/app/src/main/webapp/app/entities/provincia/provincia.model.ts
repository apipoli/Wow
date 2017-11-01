import { BaseEntity } from './../../shared';

export class Provincia implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public region?: BaseEntity,
    ) {
    }
}
