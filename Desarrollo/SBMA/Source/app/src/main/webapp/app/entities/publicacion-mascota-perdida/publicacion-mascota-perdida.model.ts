import { BaseEntity, User } from './../../shared';

export const enum EstadoMascotaPerdida {
    'PERDIDA',
    'ENCONTRADA'
}

export class PublicacionMascotaPerdida implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public lugar?: string,
        public fechaEncuentro?: any,
        public estado?: EstadoMascotaPerdida,
        public dueno?: User,
        public distrito?: BaseEntity,
        public mascota?: BaseEntity,
    ) {
    }
}
