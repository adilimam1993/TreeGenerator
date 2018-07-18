public interface Visitor<T>
{
    Visitor<T> visitTree(FiSetNode<T> tree);

    void visitData(FiSetNode<T> parent, T data);
}
