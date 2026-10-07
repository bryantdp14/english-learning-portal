export interface Vocabulary {
  id: number;
  word: string;
  meaning: string;
  exampleSentence: string;
  pronunciation: string;
  audioUrl?: string;
  level: string;
  topic: string;
  difficulty: number;
  mastery: number;
}

export interface VocabularyReviewResponse {
  words: Vocabulary[];
  totalWords: number;
  masteredWords: number;
  level: string;
}
