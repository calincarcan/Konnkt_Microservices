import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Forum } from '../../models/forum.interface';
import { ForumService } from '../../services/forum.service';

@Component({
  selector: 'app-forums',
  templateUrl: './forums.component.html',
  styleUrls: ['./forums.component.scss'],
  standalone: false
})
export class ForumsComponent implements OnInit {
  forums: Forum[] = [];
  paginatedForums: Forum[] = [];
  filteredForums: Forum[] = [];
  loading: boolean = true;
  error: string | null = null;
  searchTerm: string = '';
  
  // Pagination properties
  currentPage: number = 1;
  pageSize: number = 4;
  totalPages: number = 0;

  createForm: FormGroup;
  editingForumName: Forum | null = null;
  editingForumDescription: Forum | null = null;
  editNameForm: FormGroup;
  editDescriptionForm: FormGroup;

  constructor(
    private forumService: ForumService,
    private fb: FormBuilder
  ) {
    this.createForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });

    this.editNameForm = this.fb.group({
      name: ['', Validators.required]
    });

    this.editDescriptionForm = this.fb.group({
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadForums();
  }

  private loadForums(): void {
    this.forumService.getAllForums().subscribe({
      next: (forums) => {
        this.forums = forums;
        this.filteredForums = this.forums;
        this.totalPages = Math.ceil(this.filteredForums.length / this.pageSize);
        this.updatePaginatedForums();
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load forums';
        this.loading = false;
        console.error('Error loading forums:', err);
      }
    });
  }

  onSearch(): void {
    if (!this.searchTerm) {
      this.filteredForums = this.forums;
    } else {
      this.filteredForums = this.forums.filter(forum =>
        forum.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        forum.description.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    this.currentPage = 1;
    this.updatePaginatedForums();
  }

  updatePaginatedForums(): void {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    this.paginatedForums = this.filteredForums.slice(startIndex, startIndex + this.pageSize);
    this.totalPages = Math.ceil(this.filteredForums.length / this.pageSize);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePaginatedForums();
    }
  }

  confirmDelete(forum: Forum): void {
    if (confirm(`Are you sure you want to delete the forum "${forum.name}"?`)) {
      this.deleteForum(forum);
    }
  }

  private deleteForum(forum: Forum): void {
    this.forumService.deleteForum(forum.id).subscribe({
        next: () => {
            this.forums = this.forums.filter(f => f.id !== forum.id);
            this.updatePaginatedForums();
            this.loadForums();
        },
        error: (err) => {
            console.error('Error deleting forum:', err);
            this.error = 'Failed to delete forum';
        }
    });
  }

  onSubmit(): void {
    if (this.createForm.valid) {
      this.forumService.createForum(this.createForm.value).subscribe({
          next: () => {
              this.loadForums();
              this.createForm.reset();
          },
          error: (err) => {
              console.error('Error creating forum:', err);
              this.error = 'Failed to create forum';
          }
      });
    }
  }

  startEditingName(forum: Forum): void {
    this.editingForumName = forum;
    this.editNameForm.patchValue({
      name: forum.name
    });
  }

  startEditingDescription(forum: Forum): void {
    this.editingForumDescription = forum;
    this.editDescriptionForm.patchValue({
      description: forum.description
    });
  }

  cancelEditingName(): void {
    this.editingForumName = null;
    this.editNameForm.reset();
  }

  cancelEditingDescription(): void {
    this.editingForumDescription = null;
    this.editDescriptionForm.reset();
  }

  updateForumName(): void {
    if (!this.editingForumName || !this.editNameForm.valid) return;

    const newName = this.editNameForm.get('name')?.value;

    this.forumService.updateForumName(this.editingForumName.id, newName).subscribe({
      next: (forum) => {
        const index = this.forums.findIndex(f => f.id === forum.id);
        if (index !== -1) {
          this.forums[index] = forum;
          this.updatePaginatedForums();
        }
        this.cancelEditingName();
      },
      error: (err) => {
        console.error('Error updating forum name:', err);
        this.error = 'Failed to update forum name';
      }
    });
  }

  updateForumDescription(): void {
    if (!this.editingForumDescription || !this.editDescriptionForm.valid) return;

    const newDescription = this.editDescriptionForm.get('description')?.value;

    this.forumService.updateForumDescription(this.editingForumDescription.id, newDescription).subscribe({
      next: (forum) => {
        const index = this.forums.findIndex(f => f.id === forum.id);
        if (index !== -1) {
          this.forums[index] = forum;
          this.updatePaginatedForums();
        }
        this.cancelEditingDescription();
      },
      error: (err) => {
        console.error('Error updating forum description:', err);
        this.error = 'Failed to update forum description';
      }
    });
  }
}
