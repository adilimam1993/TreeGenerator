import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client
{
    public static void main(String[] args)
    {
        FiSetNode<String> forest = new FiSetNode<String>("Root", null);
        FiSetNode<String> current = forest;

        //I am gona pass key values from sp Adapter, [ accountIdValue, TransactionIdValue, trnamount]    //values for all this crap

        Map<String, String> value1 = new HashMap<>();
        value1.put("root_accounts", "123");
        value1.put("root_transactions", "000");

        Map<String, String> value2 = new HashMap<>();
        value2.put("root_accounts", "123");
        value2.put("desc", "BANK");

        Map<String, String> value3 = new HashMap<>();
        value3.put("root_accounts", "123");
        value3.put("name", "Adil Imam");

        Map<String, String> value4 = new HashMap<>();
        value4.put("root_accounts", "123");
        value4.put("phone", "914-243-2424");

        Map<String, String> value5 = new HashMap<>();
        value5.put("root_accounts", "999");
        value5.put("phone", "732-245-1111");

        Map<String, String> value6 = new HashMap<>();
        value6.put("root_accounts", "123");
        value6.put("root_transactions", "000");
        value6.put("trnamount", "3456357");

        Map<String, String> value7 = new HashMap<>();
        value7.put("root_accounts", "123");
        value7.put("root_transactions", "555");
        value7.put("trnamount", "1000");


        Map<String, String> value8 = new HashMap<>();
        value8.put("root_accounts", "878");
        value8.put("name", "Asad Imam");

        //This is list of rows
        String[] paths = {"root_accounts.root_transactions", "root_accounts.desc", "root_accounts.name", "root_accounts.phone", "root_accounts.phone", "root_accounts.root_transactions.trnamount", "root_accounts.root_transactions.trnamount", "root_accounts.name"};

        List<Map> listMap = new ArrayList<>();
        listMap.add(value1);
        listMap.add(value2);
        listMap.add(value3);
        listMap.add(value4);
        listMap.add(value5);
        listMap.add(value6);
        listMap.add(value7);
        listMap.add(value8);


        int iteration = 0;

        for (String tree : paths)
        {

            FiSetNode<String> root = current;

            for (String data : tree.split("\\."))
            {
                current = current.child(data, listMap.get(iteration));
            }

            current = root;
            iteration++;
        }
        forest.accept(new PrintIndentedVisitor(0));

    }



}
