package finalonee;

public class Course {
    private int id;
    private String code;
    private String name;
    private int credits;
    
    public Course() {
    }
    
    public Course(String code, String name, int credits) {
        this.code = code;
        this.name = name;
        this.credits = credits;
    }
    
    public Course(int id, String code, String name, int credits) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
    }
    

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    
    @Override
    public String toString() {
        return "Course{code='" + code + "', name='" + name + "', credits=" + credits + "}";
    }
}