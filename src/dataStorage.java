import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface dataStorage {
	 final ArrayList<String> userPass = new ArrayList<>();
	 final HashMap<Map<String, String>, Dean> deanUserPass = new HashMap<Map<String, String>, Dean>();
	 final HashMap<Map<String, String>, Professor> professorUserPass = new HashMap<Map<String, String>, Professor>();
	 final HashMap<Map<String, String>, Student> studentUserPass =  new HashMap<Map<String, String>, Student>();
	 final Collection<Dean> deanCollection = deanUserPass.values();
     final Collection<Professor> professorCollection = professorUserPass.values();
     final Collection<Student> studentCollection = studentUserPass.values();
}
