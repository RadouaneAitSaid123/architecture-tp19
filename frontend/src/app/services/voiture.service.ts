import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Voiture } from '../models/voiture.model';
import { Client } from '../models/client.model';

@Injectable({
  providedIn: 'root'
})
export class VoitureService {
  // Assuming Gateway runs on port 8888 and routes requests
  private apiUrl = 'http://localhost:8888';

  constructor(private http: HttpClient) { }

  getVoitures(): Observable<Voiture[]> {
    return this.http.get<Voiture[]>(`${this.apiUrl}/voiture-service/voitures`);
  }

  getVoituresByClient(clientId: number): Observable<Voiture[]> {
    return this.http.get<Voiture[]>(`${this.apiUrl}/voiture-service/voitures/client/${clientId}`);
  }

  getClients(): Observable<Client[]> {
      return this.http.get<Client[]>(`${this.apiUrl}/client-service/clients`);
  }

  getClientById(id: number): Observable<Client> {
      return this.http.get<Client>(`${this.apiUrl}/client-service/client/${id}`);
  }

  createVoiture(voiture: Voiture, clientId: number): Observable<Voiture> {
      return this.http.post<Voiture>(`${this.apiUrl}/voiture-service/voitures/${clientId}`, voiture);
  }

  updateVoiture(id: number, voiture: Voiture): Observable<Voiture> {
      return this.http.put<Voiture>(`${this.apiUrl}/voiture-service/voitures/${id}`, voiture);
  }
}
