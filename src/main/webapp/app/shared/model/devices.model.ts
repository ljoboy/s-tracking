import { IClients } from 'app/shared/model//clients.model';

export interface IDevices {
    id?: number;
    imei?: number;
    numeroSim?: number;
    clients?: IClients;
}

export class Devices implements IDevices {
    constructor(public id?: number, public imei?: number, public numeroSim?: number, public clients?: IClients) {}
}
