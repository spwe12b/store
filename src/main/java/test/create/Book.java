package test.create;

public class Book {
    private String name;
    private String people;
    private String number;
    private String language;
    private Book(BookBuilder bookBuilder){
        this.name=bookBuilder.name;
        this.people=bookBuilder.people;
        this.number=bookBuilder.number;
        this.language=bookBuilder.language;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", people='" + people + '\'' +
                ", number='" + number + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public static class BookBuilder{
        private String name;
        private String people;
        private String number;
        private String language;

        public BookBuilder buildName(String name){
            this.name=name;
            return this;
        }
        public BookBuilder buildPeople(String people){
            this.people=people;
            return this;
        }
        public BookBuilder buildNumber(String number){
            this.number=number;
            return this;
        }
        public BookBuilder buildLanguage(String language){
            this.language=language;
            return this;
        }
        public Book build(){
           return new Book(this);
        }
    }
}
