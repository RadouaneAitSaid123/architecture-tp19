import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VoitureService } from '../services/voiture.service';
import { Voiture } from '../models/voiture.model';
import { Client } from '../models/client.model';

@Component({
  selector: 'app-voitures',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './voitures.component.html',
  styleUrl: './voitures.component.css'
})
export class VoituresComponent implements OnInit {
  voitures: Voiture[] = [];
  clients: Client[] = [];
  selectedClientId: number | null = null; // null means 'All'

  showForm: boolean = false;
  isEditing: boolean = false;
  currentVoiture: Voiture = {
    marque: '',
    matricule: '',
    model: '',
    id_client: 0
  };

  constructor(private voitureService: VoitureService) {}

  ngOnInit(): void {
    this.fetchClients();
    this.fetchVoitures();
  }

  fetchClients(): void {
    this.voitureService.getClients().subscribe({
      next: (data) => {
        this.clients = data;
      },
      error: (err) => {
        console.error('Error fetching clients', err);
      }
    });
  }

  fetchVoitures(): void {
    if (this.selectedClientId) {
        // Note: Ensure selectedClientId is treated as a number
        this.voitureService.getVoituresByClient(Number(this.selectedClientId)).subscribe({
          next: (data) => {
            this.voitures = data;
          },
          error: (err) => {
            console.error('Error fetching voitures by client', err);
          }
        });
    } else {
        this.voitureService.getVoitures().subscribe({
          next: (data) => {
            this.voitures = data;
          },
          error: (err) => {
            console.error('Error fetching voitures', err);
          }
        });
    }
  }

  onClientChange(): void {
      this.fetchVoitures();
  }

  openAddForm(): void {
    this.isEditing = false;
    this.currentVoiture = {
      marque: '',
      matricule: '',
      model: '',
      id_client: this.clients.length > 0 ? this.clients[0].id : 0
    };
    this.showForm = true;
  }

  openEditForm(voiture: Voiture): void {
    this.isEditing = true;
    this.currentVoiture = { ...voiture };
    this.showForm = true;
  }

  cancel(): void {
    this.showForm = false;
  }

  saveVoiture(): void {
    if (this.isEditing && this.currentVoiture.id) {
      this.voitureService.updateVoiture(this.currentVoiture.id, this.currentVoiture).subscribe({
        next: () => {
          this.fetchVoitures();
          this.showForm = false;
        },
        error: (err) => console.error('Error updating voiture', err)
      });
    } else {
      this.voitureService.createVoiture(this.currentVoiture, this.currentVoiture.id_client).subscribe({
        next: () => {
          this.fetchVoitures();
          this.showForm = false;
        },
        error: (err) => console.error('Error creating voiture', err)
      });
    }
  }
}
