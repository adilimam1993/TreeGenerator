import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FiSetNode<T> implements Visitable<T> {

    // NB: LinkedHashSet preserves insertion order
    private List<FiSetNode> children = new ArrayList<>();
    private T keyName;
    private Object value;
    private static Map<String, FISetNodeInformation> fiSetMetaData = new HashMap<>();

    static
    {
        fiSetMetaData.put("root_accounts", new FISetNodeInformation(true, "acctid"));
        fiSetMetaData.put("root_transactions", new FISetNodeInformation(true, "fitid"));
        fiSetMetaData.put("phone", new FISetNodeInformation(false, null));
        fiSetMetaData.put("desc", new FISetNodeInformation(false, null));
        fiSetMetaData.put("name", new FISetNodeInformation(false, null));
        fiSetMetaData.put("acctid", new FISetNodeInformation(false, null));
        fiSetMetaData.put("trnamount", new FISetNodeInformation(false, null));
        fiSetMetaData.put("fitid", new FISetNodeInformation(false, null));
    }

    FiSetNode(T keyName, Object value)
    {
        this.keyName = keyName;
        this.value = value;
    }

    public void accept(Visitor<T> visitor)
    {
        visitor.visitData(this, keyName);

        for (FiSetNode child : children) {
            Visitor<T> childVisitor = visitor.visitTree(child);
            child.accept(childVisitor);
        }
    }

    FiSetNode child(T data, Map valueMap)   //data is the keyname itself
    {
        Object realValue = valueMap.get(data);

        for (FiSetNode child: children ) {      //this is when it has children

            Object colValue = valueMap.get(data);

            // if child.getKeyName, do a loook up in FiSetMetaData, if it exists, make sure if its list or not, if it is, then just check name // else check name and data
            //keyName have to be unique, but in our case, they are not, so we have to check the value of node as well to identify if it exists or not

            if(fiSetMetaData.get(data).isList)  //When it sees its list
            {
                if (child.keyName.equals(data))  // and it equals node name
                {
                    //return child of it
                    String listkey = fiSetMetaData.get(data).getListkey();

                    //If child with this value exists then return it, otherwise instantiate a new node
                    //colValue is the value
                    if(hasChildWithGivenNameAndValue(child, listkey, colValue))
                    {
                        return getChild(child, listkey);
                    }
                    else
                    {
                        return child.child(new FiSetNode(listkey, colValue));
                    }
                }
                else
                {
                    //Instantiate the node differently
                    FiSetNode fisetRoot = new FiSetNode(data,""); //Todo Data is the keyname in the path,
                    String listKey = fiSetMetaData.get(data).getListkey();
                    return fisetRoot.child(new FiSetNode(listKey, colValue));
                }
            }  //Or its not a list node
            else
            {
                if(child.keyName.equals(data) && child.value.equals(colValue))
                {
                    return child;
                }
            }
        }

        if(fiSetMetaData.get(data).isList)
        {
            FiSetNode fisetRoot = new FiSetNode(data,""); //Todo Data is the keyname in the path,
            String listKey = fiSetMetaData.get(data).getListkey();
            FiSetNode nodeChild = child(fisetRoot);
            FiSetNode grandChildNode = nodeChild.child(new FiSetNode(listKey, realValue));
            return grandChildNode;
        }

        return child(new FiSetNode(data, realValue));
    }

    FiSetNode child(FiSetNode<T> child)
    {
        children.add(child);
        return child;
    }

    FiSetNode getChild(FiSetNode fiSetNode, String childName)
    {
        return (FiSetNode) fiSetNode.children.stream().filter((n)->((FiSetNode)n).keyName.equals(childName)).findFirst().get();
    }

    boolean hasChildWithGivenNameAndValue(FiSetNode fiSetNode, String name, Object value)
    {
         return fiSetNode.children.stream().filter(n->((FiSetNode)n).keyName.equals(name) && ((FiSetNode)n).value.equals(value)).findFirst().isPresent();
    }
}