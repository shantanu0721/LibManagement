public class Book {
  private String title;
  private String author;
  private String genre;
  private boolean isAvailable;

  public Book(String title, String author, String genre) {
      this.title = title;
      this.author = author;
      this.genre = genre;
      this.isAvailable = true;
  }

  // Getters and setters
  public String getTitle() {
      return title;
  }

  public String getAuthor() {
      return author;
  }

  public String getGenre() {
      return genre;
  }

  public boolean isAvailable() {
      return isAvailable;
  }

  public void setAvailable(boolean isAvailable) {
      this.isAvailable = isAvailable;
  }
}
