import { BaseEntity, User } from './../../shared';

export const enum Tamano {
    'PEQUENO',
    'MEDIANO',
    'GRANDE'
}

export class Mascota implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public meses?: string,
        public tamano?: Tamano,
        public raza?: BaseEntity,
        public dueno?: User,
    ) {
    }
}
