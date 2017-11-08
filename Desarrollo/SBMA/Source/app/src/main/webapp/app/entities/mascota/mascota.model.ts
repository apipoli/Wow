import { BaseEntity, User } from './../../shared';

export const enum Tamano {
    'PEQUENO',
    'MEDIANO',
    'GRANDE'
}

export const enum Sexo {
    'MACHO',
    'HEMBRA'
}

export class Mascota implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public meses?: number,
        public tamano?: Tamano,
        public sexo?: Sexo,
        public fotoContentType?: string,
        public foto?: any,
        public raza?: BaseEntity,
        public dueno?: User,
    ) {
    }
}
