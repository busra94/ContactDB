package busra.contactdb;

/**
 * Created by busra on 22.08.2015.
 */
public class Contact {
    /** Create the Contact class.
     * This class is our model and contains the data we will save in the database and show
     * in the user interface.*/

        private String name ;
        private String surname ;
        private String phone ;
        private String email;
        private int ID ;

        public String getName () {
            return  name ;
        }

        public void setName (String name1 ) {
            this.name = name1 ;
        }

        public String getSurname () {
            return  surname ;
        }

        public void setSurname (String surname1 ) {
            this.surname = surname1;
        }

        public String getPhone () {
            return  phone ;
        }
        public void setPhone (String phone1) {
            this.phone = phone1 ;
        }

        public String getEmail () {
            return  email ;
        }

        public void setEmail (String email1 ) {
            this.email = email1 ;
        }

        public int getID(){
            return ID ;
        }
        public void setID(int ID1){
            this.ID = ID1 ;
        }

}
