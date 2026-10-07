import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/login`, { username, password });
  }

  review(userId: number, level: string, limit = 20): Observable<any> {
    return this.http.get(`${this.apiUrl}/vocabulary/review?userId=${userId}&level=${level}&limit=${limit}`);
  }

  evaluate(userId: number, vocabularyId: number, isCorrect: boolean): Observable<any> {
    return this.http.post(`${this.apiUrl}/vocabulary/evaluate?userId=${userId}`, { vocabularyId, isCorrect });
  }
}
