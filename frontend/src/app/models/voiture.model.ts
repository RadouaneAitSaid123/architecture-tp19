import { Client } from "./client.model";

export interface Voiture {
    id?: number;
    marque: string;
    matricule: string;
    model: string;
    id_client: number;
    client?: Client;
}
