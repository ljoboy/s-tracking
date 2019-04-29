import { Moment } from 'moment';
import { IDevices } from 'app/shared/model//devices.model';

export interface IHistoriques {
    id?: number;
    dateHistorique?: Moment;
    longitude?: number;
    latitude?: number;
    devices?: IDevices;
}

export class Historiques implements IHistoriques {
    constructor(
        public id?: number,
        public dateHistorique?: Moment,
        public longitude?: number,
        public latitude?: number,
        public devices?: IDevices
    ) {}
}
