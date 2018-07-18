public class FISetNodeInformation
{
    boolean isList;
    String listkey;

    public FISetNodeInformation(boolean isList, String listkey)
    {
        this.isList = isList;
        this.listkey = listkey;
    }

    public boolean isList()
    {
        return isList;
    }

    public void setList(boolean list)
    {
        isList = list;
    }

    public String getListkey()
    {
        return listkey;
    }

    public void setListkey(String listkey)
    {
        this.listkey = listkey;
    }
}
