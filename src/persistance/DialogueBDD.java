package persistance;

import java.sql.ResultSet;
import java.sql.Statement;

import model.data.City;

public class DialogueBDD {

	public static void main(){
		
	}
	public City recupereVille(){
		Statement statement = Connexion.getConnexion().createStatement();
        ResultSet rs = statement.executeQuery("select * from city");
        while (rs.next()) {
            int idcity = rs.getInt("idcity");
            long latitude = rs.getLong("latitude");
            long longitude = rs.getLong("longitude");
            int idcountry = rs.getInt("idcountry");
            System.out.println(idcity + "\t" + latitude +
                               "\t" + longitude + "\t" + idcountry);
        }
	}
}
