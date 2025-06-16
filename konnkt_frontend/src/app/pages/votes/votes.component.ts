import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Vote } from '../../models/vote.interface';
import { VoteService } from '../../services/vote.service';

@Component({
  selector: 'app-votes',
  templateUrl: './votes.component.html',
  styleUrls: ['./votes.component.scss'],
  standalone: false
})
export class VotesComponent implements OnInit {
  votes: Vote[] = [];
  paginatedVotes: Vote[] = [];
  filteredVotes: Vote[] = [];
  loading: boolean = true;
  error: string | null = null;
  searchTerm: string = '';
  
  // Pagination properties
  currentPage: number = 1;
  pageSize: number = 4;
  totalPages: number = 0;

  createForm: FormGroup;

  constructor(
    private voteService: VoteService,
    private fb: FormBuilder
  ) {
    this.createForm = this.fb.group({
      parentType: ['post', Validators.required],
      parentId: ['', Validators.required],
      voteType: [1, Validators.required]  // Default to upvote
    });
  }

  ngOnInit(): void {
    this.loadVotes();
  }

  private loadVotes(): void {
    this.voteService.getAllVotes().subscribe({
      next: (votes) => {
        this.votes = votes;
        this.filteredVotes = this.votes;
        this.totalPages = Math.ceil(this.filteredVotes.length / this.pageSize);
        this.updatePaginatedVotes();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load votes';
        this.loading = false;
        console.error('Error loading votes:', err);
      }
    });
  }

  onSearch(): void {
    if (!this.searchTerm) {
      this.filteredVotes = this.votes;
    } else {
      this.filteredVotes = this.votes.filter(vote =>
        vote.parentType.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        vote.voteType.toString().includes(this.searchTerm)
      );
    }
    this.currentPage = 1;
    this.updatePaginatedVotes();
  }

  updatePaginatedVotes(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedVotes = this.filteredVotes.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.filteredVotes.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePaginatedVotes();
    }
  }

  confirmDelete(vote: Vote): void {
    if (confirm(`Are you sure you want to delete this vote?\n\nVote on ${vote.parentType} (ID: ${vote.parentId})\nVote Type: ${vote.voteType}`)) {
      this.deleteVote(vote);
    }
  }

  private deleteVote(vote: Vote): void {
    this.voteService.deleteVote(vote.id).subscribe({
        next: () => {
            this.votes = this.votes.filter(v => v.id !== vote.id);
            this.updatePaginatedVotes();
            this.loadVotes();
        },
        error: (err) => {
            console.error('Error deleting vote:', err);
            this.error = 'Failed to delete vote';
        }
    });
  }

  onSubmit(): void {
    if (this.createForm.valid) {
      const vote: Vote = {
        ...this.createForm.value,
        voteType: parseInt(this.createForm.value.voteType) // Ensure voteType is a number
      };

      this.voteService.createVote(vote).subscribe({
          next: () => {
              this.loadVotes();
              this.createForm.reset({
                  parentType: 'post',
                  voteType: 1
              });
          },
          error: (err) => {
              console.error('Error creating vote:', err);
              this.error = 'Failed to create vote';
          }
      });
    }
  }

  changeVoteType(vote: Vote): void {
    this.voteService.changeVote(vote.id).subscribe({
      next: (updatedVote) => {
        // Update the vote in the list
        const index = this.votes.findIndex(v => v.id === vote.id);
        if (index !== -1) {
          this.votes[index] = updatedVote;
          this.updatePaginatedVotes();
        }
      },
      error: (err) => {
        console.error('Error changing vote:', err);
        this.error = 'Failed to change vote';
      }
    });
  }
}
