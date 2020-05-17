import java.io.PrintStream;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;
    private Classes cls;
    private Classes basicCls;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */
	basicCls = new Classes(0);
	basicCls = basicCls.appendElement(Object_class);
	basicCls = basicCls.appendElement(IO_class);
	basicCls = basicCls.appendElement(Int_class);
	basicCls = basicCls.appendElement(Bool_class);
	basicCls = basicCls.appendElement(Str_class);
    }

    public ClassTable(Classes cls) {
	semantErrors = 0;
	errorStream = System.err;
	this.cls = cls;
	this.installBasicClasses();
	/* fill this in */
    }

    public void semant(){
	List<CheckingInherit> checkList = new ArrayList<CheckingInherit>();
	this.FillCheckList(checkList, this.basicCls);
	this.FillCheckList(checkList, this.cls);

	for(int i=0;i<checkList.size();i++){
	    if(checkList.get(i).state == 0){
		processCheckList(checkList, checkList.get(i));
	    }
	}
    }

    public boolean hasClass(AbstractSymbol className){
	return this.getClass(className) != null;
    }

    public boolean hasMethod(AbstractSymbol className, AbstractSymbol methodName,  List<formalc> params, AbstractSymbol result){
	return false;
    }

    private class_c getClass(AbstractSymbol className){
	for(Enumeration e = this.cls.getElements(); e.hasMoreElements();) {
	    CheckingInherit item = new CheckingInherit();
	    class_c c = (class_c)e.nextElement();
	    if(c.getName() == className){
		return c;
	    }
	}
	for(Enumeration e = this.basicCls.getElements(); e.hasMoreElements();) {
	    CheckingInherit item = new CheckingInherit();
	    class_c c = (class_c)e.nextElement();
	    if(c.getName() == className){
		return c;
	    }
	}
	return null;
    }

    // recursively go through classes and detect circular inheritance
    private boolean processCheckList(List<CheckingInherit> checkList, CheckingInherit item)
    {
	if(item.name == TreeConstants.Object_){
	    // we have reached root of inheritance, everything is good
	    item.state = 2;
	    return true;
	}

	if(item.state == 2){
	    // we have reached an already checked class
	    return true;
	}

	if(item.state == 1){
	    // we have reached a class that we are already trying to proccess
	    // it is a circular inheritance
	    semantError(item.classItem);
	    errorStream.println("Class " + item.name + " has circular inheritance");    
	    item.state = 2;
	    return false;
	}
	    
	item.state = 1; //mark state as being processed
	CheckingInherit parentItem = null;
	for(int i=0;i<checkList.size();i++){
	    if(checkList.get(i).name == item.parent){
		parentItem = checkList.get(i);
	    }
	}
	if(parentItem == null){
	    semantError(item.classItem);
	    errorStream.println("Class " + item.parent + " not found. It is a super class of " + item.name);
	    item.state = 2;
	    return false;
	}

	if(processCheckList(checkList, parentItem)){
	    item.state = 2;
	    return true;
	} else {
	    item.state = 2;
	    return false;
	}
    }

    private void FillCheckList(List<CheckingInherit> checkList, Classes classes){
	for(Enumeration e = classes.getElements(); e.hasMoreElements();) {
	    CheckingInherit item = new CheckingInherit();
	    class_c c = (class_c)e.nextElement();
	    item.name = c.getName();
	    item.state = 0;
	    item.parent = c.getParent();
	    item.classItem = c;
	    boolean hasDuplicate = false;	
	    for(int i=0;i<checkList.size();i++){

		if(checkList.get(i).name == item.name){
		    semantError(c);
		    errorStream.println("Class " + item.name + " was previously defined.");    
		    hasDuplicate = true;
		}
	    }
	    if(!hasDuplicate){
	        checkList.add(item);
	    }
	}
    }

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }
}

class CheckingInherit {
    public AbstractSymbol name;
    public AbstractSymbol parent;
    // 0 - not processed, 1 - processing, 2 - ok
    public int state;
    // keep class record to report errors in a nice way
    public class_c classItem;
}

    
