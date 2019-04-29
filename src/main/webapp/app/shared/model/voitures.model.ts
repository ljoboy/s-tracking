import { IDevices } from 'app/shared/model//devices.model';

export interface IVoitures {
    id?: number;
    plaque?: string;
    marque?: string;
    couleur?: string;
    devices?: IDevices;
}

export class Voitures implements IVoitures {
    constructor(public id?: number, public plaque?: string, public marque?: string, public couleur?: string, public devices?: IDevices) {}
}
