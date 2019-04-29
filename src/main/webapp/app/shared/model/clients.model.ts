import { Moment } from 'moment';

export const enum Types {
    PARTICULIER = 'PARTICULIER',
    ENTREPRISE = 'ENTREPRISE'
}

export interface IClients {
    id?: number;
    nom?: string;
    dateAbonnement?: Moment;
    province?: string;
    ville?: string;
    adresse?: string;
    password?: string;
    types?: Types;
    telephone?: number;
    email?: string;
    rccm?: string;
}

export class Clients implements IClients {
    constructor(
        public id?: number,
        public nom?: string,
        public dateAbonnement?: Moment,
        public province?: string,
        public ville?: string,
        public adresse?: string,
        public password?: string,
        public types?: Types,
        public telephone?: number,
        public email?: string,
        public rccm?: string
    ) {}
}
