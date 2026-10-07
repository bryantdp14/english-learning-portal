import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  standalone: true,
  template: `
    <main class="layout">
      <section class="hero">
        <div class="badge">English Learning Portal</div>
        <h1>Learn English with structure, practice and progress</h1>
        <p>
          Vocabulary, grammar, listening, and interactive exercises to improve your English in a simple and motivating portal.
        </p>
        <div class="actions">
          <button>Start learning</button>
          <button class="secondary">View courses</button>
        </div>
      </section>

      <section class="cards">
        <article>
          <h3>Vocabulary</h3>
          <p>Build vocabulary by level and context.</p>
        </article>
        <article>
          <h3>Grammar</h3>
          <p>Practice rules with interactive lessons.</p>
        </article>
        <article>
          <h3>Listening</h3>
          <p>Improve comprehension with audio activities.</p>
        </article>
      </section>
    </main>
  `,
  styles: `
    :host {
      display: block;
      font-family: Arial, sans-serif;
      background: linear-gradient(135deg, #eef6ff, #f5f0ff);
      min-height: 100vh;
      color: #1f2937;
    }

    .layout {
      max-width: 1100px;
      margin: 0 auto;
      padding: 80px 24px;
    }

    .hero {
      background: white;
      border-radius: 24px;
      padding: 48px;
      box-shadow: 0 20px 40px rgba(15, 23, 42, 0.08);
    }

    .badge {
      display: inline-block;
      background: #dbeafe;
      color: #1d4ed8;
      border-radius: 999px;
      padding: 8px 14px;
      font-size: 12px;
      font-weight: 700;
      letter-spacing: 0.06em;
      text-transform: uppercase;
    }

    h1 {
      font-size: clamp(2.5rem, 5vw, 4rem);
      margin: 20px 0 12px;
      line-height: 1.1;
    }

    p {
      font-size: 1.05rem;
      line-height: 1.7;
      color: #475569;
      max-width: 700px;
    }

    .actions {
      display: flex;
      gap: 16px;
      margin-top: 28px;
      flex-wrap: wrap;
    }

    button {
      border: none;
      background: #2563eb;
      color: white;
      padding: 14px 22px;
      border-radius: 12px;
      font-weight: 700;
      cursor: pointer;
    }

    .secondary {
      background: #e2e8f0;
      color: #0f172a;
    }

    .cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 20px;
      margin-top: 32px;
    }

    .cards article {
      background: white;
      border-radius: 20px;
      padding: 24px;
      box-shadow: 0 14px 30px rgba(15, 23, 42, 0.05);
    }

    h3 {
      margin-top: 0;
      font-size: 1.35rem;
    }
  `
})
export class AppComponent {}
