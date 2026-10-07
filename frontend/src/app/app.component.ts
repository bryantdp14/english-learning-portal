import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from './api.service';
import { Vocabulary } from './models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  template: `
    <div class="app-shell">
      <header class="topbar" *ngIf="loggedIn">
        <div class="brand">English Learning Portal</div>
        <div class="nav">
          <button class="nav-btn active">Vocabulary</button>
          <button class="nav-btn">Courses</button>
          <button class="nav-btn">Progress</button>
        </div>
        <button class="logout-btn" (click)="logout()">Log out</button>
      </header>

      <main class="content" *ngIf="!loggedIn; else dashboard">
        <section class="auth-panel">
          <div class="auth-copy">
            <span class="chip">Learn English</span>
            <h1>Practice vocabulary, grammar and listening</h1>
            <p>
              A modern portal to improve your English with dynamic lessons,
              repeated vocabulary, and measurable progress.
            </p>
            <ul>
              <li>Spaced repetition</li>
              <li>Progress tracking</li>
              <li>Ranking and achievements</li>
            </ul>
          </div>

          <div class="login-card">
            <h2>Welcome back</h2>
            <label>
              Username
              <input [(ngModel)]="username" placeholder="student01" />
            </label>
            <label>
              Password
              <input type="password" [(ngModel)]="password" placeholder="••••••••" />
            </label>
            <button class="primary-btn" (click)="login()" [disabled]="loading">
              {{ loading ? 'Signing in...' : 'Sign in' }}
            </button>
            <button class="secondary-btn" (click)="demoLogin()">Use demo user</button>
            <p class="status" *ngIf="errorMessage">{{ errorMessage }}</p>
          </div>
        </section>
      </main>

      <ng-template #dashboard>
        <section class="dashboard">
          <div class="stats-row">
            <article class="stat-card">
              <span>Level</span>
              <strong>{{ selectedLevel }}</strong>
            </article>
            <article class="stat-card">
              <span>Words</span>
              <strong>{{ reviewResponse?.totalWords ?? 0 }}</strong>
            </article>
            <article class="stat-card">
              <span>Mastered</span>
              <strong>{{ reviewResponse?.masteredWords ?? 0 }}</strong>
            </article>
            <article class="stat-card">
              <span>Progress</span>
              <strong>{{ progressPercent }}%</strong>
            </article>
          </div>

          <div class="toolbar">
            <div class="level-group">
              <label>Level</label>
              <select [(ngModel)]="selectedLevel" (change)="loadVocabulary()">
                <option value="A1">A1</option>
                <option value="A2">A2</option>
                <option value="B1">B1</option>
                <option value="B2">B2</option>
              </select>
            </div>
            <button class="primary-btn" (click)="loadVocabulary()">Refresh words</button>
          </div>

          <div class="vocabulary-grid">
            <article class="word-card" *ngFor="let word of reviewResponse?.words ?? []">
              <div class="word-header">
                <h3>{{ word.word }}</h3>
                <span class="pill">{{ word.level }}</span>
              </div>
              <p class="meaning">{{ word.meaning }}</p>
              <p class="example">Example: {{ word.exampleSentence }}</p>
              <div class="meta-row">
                <span>Topic: {{ word.topic }}</span>
                <span>Mastery: {{ word.mastery }}%</span>
              </div>
              <div class="actions">
                <button class="success" (click)="evaluateWord(word, true)">I know it</button>
                <button class="warning" (click)="evaluateWord(word, false)">Review</button>
              </div>
            </article>
          </div>

          <div class="empty-state" *ngIf="!(reviewResponse?.words?.length)">
            No words available for this level yet. Try another level or add new vocabulary.
          </div>
        </section>
      </ng-template>
    </div>
  `,
  styles: [
    `
      :host {
        --bg: #f4f7ff;
        --card: #ffffff;
        --primary: #2557ff;
        --primary-dark: #1a3fc7;
        --text: #0f172a;
        --muted: #64748b;
        --success: #1fbf75;
        --warning: #ffb703;
        --danger: #ef4444;
      }

      * { box-sizing: border-box; }

      body {
        margin: 0;
        font-family: Inter, Arial, sans-serif;
      }

      .app-shell {
        min-height: 100vh;
        background: linear-gradient(135deg, #eef5ff 0%, #edf6ff 100%);
        color: var(--text);
      }

      .topbar {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 18px 36px;
        background: rgba(255,255,255,0.8);
        backdrop-filter: blur(10px);
        border-bottom: 1px solid rgba(148, 163, 184, 0.2);
      }

      .brand {
        font-size: 1.25rem;
        font-weight: 800;
      }

      .nav {
        display: flex;
        gap: 10px;
      }

      .nav-btn, .logout-btn, .primary-btn, .secondary-btn, .success, .warning {
        border: none;
        border-radius: 12px;
        padding: 10px 16px;
        cursor: pointer;
        font-weight: 700;
      }

      .nav-btn {
        background: #e7edff;
        color: var(--text);
      }

      .nav-btn.active {
        background: var(--primary);
        color: white;
      }

      .logout-btn {
        background: #eef2ff;
        color: var(--text);
      }

      .content {
        max-width: 1200px;
        margin: 0 auto;
        padding: 40px 20px;
      }

      .auth-panel {
        display: grid;
        grid-template-columns: 1.25fr 1fr;
        gap: 24px;
        align-items: center;
      }

      .auth-copy, .login-card, .stat-card, .word-card, .empty-state {
        background: var(--card);
        border-radius: 24px;
        box-shadow: 0 18px 40px rgba(15, 23, 42, 0.08);
      }

      .auth-copy {
        padding: 42px;
      }

      .chip {
        display: inline-block;
        background: #dbeafe;
        color: #1d4ed8;
        border-radius: 999px;
        padding: 8px 14px;
        font-size: 12px;
        font-weight: 700;
        letter-spacing: 0.08em;
        text-transform: uppercase;
      }

      h1 {
        font-size: clamp(2.8rem, 4vw, 4rem);
        margin: 20px 0 18px;
        line-height: 1.1;
      }

      .auth-copy p {
        color: var(--muted);
        font-size: 1.05rem;
        line-height: 1.7;
      }

      .auth-copy ul {
        margin-top: 22px;
        padding-left: 18px;
        color: var(--text);
        line-height: 2;
      }

      .login-card {
        padding: 32px 28px;
      }

      .login-card h2 {
        margin-top: 0;
      }

      label {
        display: block;
        margin-top: 18px;
        color: var(--muted);
        font-size: 0.94rem;
      }

      input, select {
        width: 100%;
        margin-top: 10px;
        border: 1px solid #dfe7f5;
        border-radius: 12px;
        padding: 12px 14px;
        font-size: 1rem;
        background: #f8fbff;
      }

      .primary-btn {
        width: 100%;
        background: var(--primary);
        color: white;
        margin-top: 24px;
      }

      .secondary-btn {
        width: 100%;
        margin-top: 12px;
        background: #e8eefc;
        color: var(--text);
      }

      .status {
        margin-top: 16px;
        color: var(--danger);
        font-weight: 600;
      }

      .dashboard {
        max-width: 1200px;
        margin: 0 auto;
        padding: 30px 20px 50px;
      }

      .stats-row {
        display: grid;
        grid-template-columns: repeat(4, minmax(180px, 1fr));
        gap: 18px;
      }

      .stat-card {
        padding: 22px 20px;
      }

      .stat-card span {
        display: block;
        color: var(--muted);
        font-size: 0.84rem;
      }

      .stat-card strong {
        display: block;
        margin-top: 12px;
        font-size: 2rem;
      }

      .toolbar {
        display: flex;
        align-items: end;
        justify-content: space-between;
        margin-top: 24px;
        background: rgba(255,255,255,0.7);
        border-radius: 18px;
        padding: 18px 20px;
      }

      .level-group {
        width: 180px;
      }

      .level-group label {
        color: var(--muted);
        font-size: 0.9rem;
      }

      .toolbar .primary-btn {
        width: auto;
        margin-top: 0;
      }

      .vocabulary-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
        gap: 20px;
        margin-top: 26px;
      }

      .word-card {
        padding: 20px;
      }

      .word-header {
        display: flex;
        justify-content: space-between;
        gap: 12px;
        align-items: center;
      }

      .word-header h3 {
        margin: 0;
        font-size: 1.6rem;
      }

      .pill {
        background: #dbeafe;
        color: #1d4ed8;
        padding: 6px 10px;
        border-radius: 999px;
        font-size: 0.7rem;
        font-weight: 800;
      }

      .meaning {
        margin: 18px 0 8px;
        font-size: 1.05rem;
        color: #1e293b;
      }

      .example {
        color: var(--muted);
        line-height: 1.6;
      }

      .meta-row {
        display: flex;
        justify-content: space-between;
        gap: 12px;
        color: var(--muted);
        font-size: 0.82rem;
        margin-top: 16px;
      }

      .actions {
        display: flex;
        gap: 10px;
        margin-top: 18px;
      }

      .success {
        background: rgba(31, 191, 117, 0.15);
        color: #15803d;
        flex: 1;
      }

      .warning {
        background: rgba(255, 183, 3, 0.15);
        color: #b45309;
        flex: 1;
      }

      .empty-state {
        margin-top: 30px;
        padding: 24px;
        text-align: center;
        color: var(--muted);
        font-weight: 600;
      }

      @media (max-width: 800px) {
        .auth-panel {
          grid-template-columns: 1fr;
        }

        .stats-row {
          grid-template-columns: repeat(2, minmax(140px, 1fr));
        }

        .topbar {
          flex-wrap: wrap;
          gap: 14px;
          justify-content: center;
        }
      }
    `
  ]
})
export class AppComponent {
  loggedIn = false;
  loading = false;
  username = '';
  password = '';
  errorMessage = '';
  selectedLevel = 'A1';
  userId = 1;
  reviewResponse: any = { words: [], totalWords: 0, masteredWords: 0, level: 'A1' };

  constructor(private api: ApiService) {}

  get progressPercent(): number {
    const total = this.reviewResponse?.totalWords || 0;
    const mastered = this.reviewResponse?.masteredWords || 0;
    if (!total) return 0;
    return Math.round((mastered / total) * 100);
  }

  demoLogin(): void {
    this.username = 'student01';
    this.password = 'password123';
    this.login();
  }

  login(): void {
    if (!this.username || !this.password) {
      this.errorMessage = 'Username and password are required.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.api.login(this.username, this.password).subscribe({
      next: (response: any) => {
        this.loggedIn = true;
        this.loading = false;
        localStorage.setItem('english-portal-token', response.token || 'demo-token');
        localStorage.setItem('english-portal-user', this.username);
        this.loadVocabulary();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Invalid username or password. Try the demo user.';
      }
    });
  }

  logout(): void {
    this.loggedIn = false;
    this.username = '';
    this.password = '';
    this.errorMessage = '';
    localStorage.removeItem('english-portal-token');
    localStorage.removeItem('english-portal-user');
  }

  loadVocabulary(): void {
    this.api.review(this.userId, this.selectedLevel, 20).subscribe({
      next: (response: any) => {
        this.reviewResponse = response;
      },
      error: () => {
        this.reviewResponse = { words: [], totalWords: 0, masteredWords: 0, level: this.selectedLevel };
      }
    });
  }

  evaluateWord(word: Vocabulary, isCorrect: boolean): void {
    this.api.evaluate(this.userId, word.id, isCorrect).subscribe({
      next: () => {
        this.loadVocabulary();
      },
      error: () => {
        this.errorMessage = 'Could not evaluate the word. Please try again.';
      }
    });
  }
}
