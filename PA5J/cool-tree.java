// -*- mode: java
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////



import java.util.Enumeration;
import java.io.PrintStream;
import java.util.*;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();
    public abstract void cgen(PrintStream s);

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
    public abstract void code(PrintStream s);
    public abstract int countActiveVariables();

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'program'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class program extends Program {
    public Classes classes;
    /** Creates "program" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public program(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new program(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "program\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    ((Class_)e.nextElement()).dump_with_types(out, n + 1);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
	/* ClassTable constructor may do some semantic analysis */
	ClassTable classTable = new ClassTable(classes);
	
	/* some semantic analysis code may go here */

	if (classTable.errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
	}
    }
    /** This method is the entry point to the code generator.  All of the work
      * of the code generator takes place within CgenClassTable constructor.
      * @param s the output stream 
      * @see CgenClassTable
      * */
    public void cgen(PrintStream s) 
    {
        // spim wants comments to start with '#'
        s.print("# start of generated code\n");

	CgenClassTable codegen_classtable = new CgenClassTable(classes, s);

	s.print("\n# end of generated code\n");
    }

}


/** Defines AST constructor 'class_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_ extends Class_ {
    public AbstractSymbol name;
    public AbstractSymbol parent;
    public Features features;
    public AbstractSymbol filename;
    /** Creates "class_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }

    public void codeInitMethod(PrintStream str){
	//count number of variables
	int maxActiveVariables = 0;
	List<AttributeDescription> attributes = CgenContext.currentClass.getAttributes();
	for(int i=0;i<attributes.size();++i){
	    AttributeDescription attribute = attributes.get(i);
	    int n = attribute.initExpr.countActiveVariables();
		if(n > maxActiveVariables){
		    maxActiveVariables = n;
		}
	}
	
	CgenContext.selfObjectOffset = maxActiveVariables;
	CgenScope.setMaxObjectsCount(maxActiveVariables);

	//generate code
	//store stack pointer to $t1, it will be used as a frame pointer
	CgenSupport.emitMove(CgenSupport.T1, CgenSupport.SP, str);
	//reserve space for local variables in stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, maxActiveVariables * CgenSupport.WORD_SIZE * (-1), str);
	CgenSupport.emitPush(CgenSupport.ACC, str);//put self object to stack from $a0
	CgenSupport.emitPush(CgenSupport.FP, str);//store old frame pointer to stack
	CgenSupport.emitPush(CgenSupport.RA, str);// store return address in stack, execution of caller will continue at this address	
	CgenSupport.emitMove(CgenSupport.FP, CgenSupport.T1, str);//set new frame pointer

	str.println("\t# start of object init body");
	for(int i=0;i<attributes.size();++i){
	    AttributeDescription attribute = attributes.get(i);
	    if(!(attribute.initExpr instanceof no_expr)){
		ObjectDescription desc = CgenScope.getObjectDescription(attribute.name);
		str.println("\t#init for " + attribute.name);
		attribute.initExpr.code(str);
		str.println("\t# assign value from $a0 to " + attribute.name);
		//load self address to $t1
		CgenSupport.emitLoad(CgenSupport.T1, -CgenContext.selfObjectOffset, CgenSupport.FP, str);
		//add attribute offset to $t1
		CgenSupport.emitAddiu(CgenSupport.T1, CgenSupport.T1, 
		    (CgenSupport.DEFAULT_OBJFIELDS + desc.offset) * CgenSupport.WORD_SIZE, str);
		//load expression value from $a0 to address in $t1
		CgenSupport.emitStore(CgenSupport.ACC, 0,CgenSupport.T1, str);
	    }
	}
	str.println("\t# end of object init body");
	CgenSupport.emitLoad(CgenSupport.RA, 1, CgenSupport.SP, str);//restore return address
	CgenSupport.emitLoad(CgenSupport.FP, 2, CgenSupport.SP, str);//restore old frame pointer
	CgenSupport.emitLoad(CgenSupport.ACC, 3, CgenSupport.SP, str);//put self object to $a0
	//frame size inclues return addr, frame pointer, self object, formals and variables
	int frameSize = (3 + maxActiveVariables) * CgenSupport.WORD_SIZE;
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, frameSize, str);//remove activation record from stack
	CgenSupport.emitReturn(str);//return control to caller
    }
}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    public AbstractSymbol name;
    public Formals formals;
    public AbstractSymbol return_type;
    public Expression expr;
    //ammount of maximum simultaneously active variables that need space in an activation record
    public int variablesCount;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
	variablesCount = 0;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }
    public AbstractSymbol getName(){
	return this.name;
    }
    public void code(PrintStream str){
	// set new frame pointer address to the first formal and store it in $t1
	CgenSupport.emitAddiu(CgenSupport.T1, CgenSupport.SP, this.formals.getLength() * CgenSupport.WORD_SIZE, str);
	//reserve space for local variables in stack
	str.println("\t# slots for variables: " + this.variablesCount);
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, this.variablesCount * CgenSupport.WORD_SIZE * (-1), str);
	CgenSupport.emitPush(CgenSupport.ACC, str);//put self object to stack from $a0
	CgenSupport.emitPush(CgenSupport.FP, str);//store old frame pointer to stack
	CgenSupport.emitPush(CgenSupport.RA, str);// store return address in stack, execution of caller will continue at this address
	CgenSupport.emitMove(CgenSupport.FP, CgenSupport.T1, str);//set new frame pointer
	str.println("\t# start of method body");
	this.expr.code(str);
	str.println("\t# end of method body");
	CgenSupport.emitLoad(CgenSupport.RA, 1, CgenSupport.SP, str);//restore return address
	CgenSupport.emitLoad(CgenSupport.FP, 2, CgenSupport.SP, str);//restore old frame pointer
	//frame size inclues return addr, frame pointer, self object, formals and variables
	int frameSize = (3 + this.formals.getLength() + this.variablesCount) * CgenSupport.WORD_SIZE;
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, frameSize, str);//remove activation record from stack
	CgenSupport.emitReturn(str);//return control to caller
    }

    public void countVariables(){
	this.variablesCount = this.expr.countActiveVariables();
    }
}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

    public AbstractSymbol getName(){
	return this.name;
    }

}


/** Defines AST constructor 'formal'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formal extends Formal {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    /** Creates "formal" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formal(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formal(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formal\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    public AbstractSymbol name;
    public AbstractSymbol type_decl;
    public Expression expr;
    public CaseClassDescription desc;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }

    public void code(PrintStream s,int labelCaseEnd){
	//assume argument tag in $t2 and argument value in $a0
	s.println("\t# branch " + this.name);
	
	int labelBranchMatch = CgenContext.labelNumber;
	CgenContext.labelNumber++;

	s.println("\t# go through all subtypes of " + type_decl + " and compare it to arguments type");
	for(int i = 0;i < this.desc.childClassTags.size(); ++i){
	    int classTag = this.desc.childClassTags.get(i);
	    CgenSupport.emitBeq(CgenSupport.T2, Integer.toString(classTag), labelBranchMatch, s);
	}
	
	s.println("\t#if we reach here, branch doesnt match");
	int labelBranchDoesntMatch = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBranch(labelBranchDoesntMatch, s);
	
	s.println("\t# jump here if branch matches argument");
	CgenSupport.emitLabelDef(labelBranchMatch, s);	
	
	CgenScope.enterScope();
	CgenScope.addObject(this.name);
	s.println("\t# assign value from $a0 to " + this.name);
	ObjectDescription desc = CgenScope.getObjectDescription(this.name);
	CgenSupport.emitStore(CgenSupport.ACC, -desc.offset,CgenSupport.FP, s);
    
	s.println("\t# evaluate body of branch");
	this.expr.code(s);
	CgenScope.exitScope();
	s.println("\t# jump to end of case");
	CgenSupport.emitBranch(labelCaseEnd, s);

	CgenSupport.emitLabelDef(labelBranchDoesntMatch, s);	
	s.println("\t#branch end " + this.name);
    }

    public int countActiveVariables(){
	return this.expr.countActiveVariables() + 1;//branch intoduces a variable
    }

}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    public AbstractSymbol name;
    public Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
	expr.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
    }

    public int countActiveVariables(){
	return this.expr.countActiveVariables();
    }
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    public Expression expr;
    public AbstractSymbol type_name;
    public AbstractSymbol name;
    public Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
    }

    public int countActiveVariables(){
	int result = this.expr.countActiveVariables();

	for(int i = 0; i<this.actual.getLength(); ++i){
    	    Expression e = (Expression)this.actual.getNth(i);	    
    	    int n = e.countActiveVariables();
	    if(n>result){
		result = n;
	    }
	}

	return result;
    }
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    public Expression expr;
    public AbstractSymbol name;
    public Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start dispatch to " + this.name);
	//evaluate formals and put their values to stack
	int paramNumber = 0;
	for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    Expression ex = (Expression)e.nextElement();
	    s.println("\t# evaluating formal parameter " + paramNumber + " and putting it to stack");
	    ex.code(s);
	    CgenSupport.emitPush(CgenSupport.ACC, s);//store param value in stack
        }
	//evaluate expression
	s.println("\t# evaluating dispatch object");
	this.expr.code(s);
	
	//check for void
	s.println("\t# checking dispatch object for void");
	int label = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, label, s);
	s.println("\t# terminate on void dispatch");
	CgenSupport.emitLoadAddress(CgenSupport.ACC, "str_const0", s);//error message
	CgenSupport.emitLoadImm(CgenSupport.T1, this.lineNumber, s);
	CgenSupport.emitJal("_dispatch_abort", s);

	CgenSupport.emitLabelDef(label, s);

	//get method label from a class table
	AbstractSymbol objectType = this.expr.get_type();
	if(objectType == TreeConstants.SELF_TYPE){
	    objectType = CgenContext.currentClassName;
	}
	
	int labelIndex = CgenContext.classTable.getMethodIndex(objectType, this.name);
	s.println("\t# load dispatch reference from object to $t1");
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DISPTABLE_OFFSET, CgenSupport.ACC, s);
	s.println("\t# load method label from dispatch table to $t1. class " + objectType + " method "
	    + this.name);
	CgenSupport.emitLoad(CgenSupport.T1, labelIndex, CgenSupport.T1, s);

	//give control to a callee
	s.println("\t# give control to a callee");
	CgenSupport.emitJalr(CgenSupport.T1, s);
	s.println("\t# end dispatch to " + this.name);
    }

    public int countActiveVariables(){
	int result = this.expr.countActiveVariables();

	for(int i = 0; i<this.actual.getLength(); ++i){
    	    Expression e = (Expression)this.actual.getNth(i);	    
    	    int n = e.countActiveVariables();
	    if(n>result){
		result = n;
	    }
	}

	return result;
    }
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    public Expression pred;
    public Expression then_exp;
    public Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start if. Evaluate predicate");
	this.pred.code(s);
	s.println("\t# load value from a Bool evaluation result object to $t1");
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);

	int labelFalse = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBeqz(CgenSupport.T1, labelFalse, s);
	s.println("\t# this code executes if condition is true");
	this.then_exp.code(s);
	
	int labelFi = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBranch(labelFi, s);

	CgenSupport.emitLabelDef(labelFalse, s);
	s.println("\t# this code executes if condition is false");
	this.else_exp.code(s);
	
	CgenSupport.emitLabelDef(labelFi, s);
	s.println("\t# end if");
    }

    public int countActiveVariables(){
	int result =  this.pred.countActiveVariables();
	int nthen = this.then_exp.countActiveVariables();
	int nelse = this.else_exp.countActiveVariables();
	if(nthen > result){
	    result = nthen;
	}
	if(nelse > result){
	    result = nelse;
	}

	return result;
    }
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    public Expression pred;
    public Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
    }

    public int countActiveVariables(){
	int npred = this.pred.countActiveVariables();
	int nbody = this.body.countActiveVariables();
	if(npred > nbody){
	    return npred;
	} else {
	    return nbody;
	}
    }
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    public Expression expr;
    public Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start case, evaluate expression");
	this.expr.code(s);

	s.println("\t# checking argument for void");
	int labelNotVoid = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.ZERO, labelNotVoid, s);
	s.println("\t# terminate on void");
	CgenSupport.emitLoadAddress(CgenSupport.ACC, "str_const0", s);//error message
	CgenSupport.emitLoadImm(CgenSupport.T1, this.lineNumber, s);
	CgenSupport.emitJal("_case_abort2", s);
	CgenSupport.emitLabelDef(labelNotVoid, s);
	
	s.println("\t#argument is not void, continue here");
	s.println("\t#load argument class tag to t2");
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.TAG_OFFSET, CgenSupport.ACC, s);
	
	//find out in which order to apply branches. Branches with the most specific types, 
	//furtherest from Object in hierarchy, go first.
	List<branch> branchList = new ArrayList<branch>();
	for(int i = 0; i<this.cases.getLength(); ++i){
    	    branch b = (branch)this.cases.getNth(i);
	    b.desc = CgenContext.classTable.getCaseClassDescription(b.type_decl);
	    branchList.add(b);
	}

	Collections.sort(branchList, new Comparator<branch>(){
    	    public int compare(branch b1, branch b2){
        	if(b1.desc.hierarcyLevel == b2.desc.hierarcyLevel)
	             return 0;
        	return b1.desc.hierarcyLevel > b2.desc.hierarcyLevel ? -1 : 1;
    	    }
	});


	int labelCaseEnd = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	for(int i = 0;i < branchList.size(); ++i){
    	    branch b = branchList.get(i);
    	    //proccess branch
	    b.code(s, labelCaseEnd);
	}

	s.println("\t# no branches match if we are here, show error");
	CgenSupport.emitJal("_case_abort", s);	

	CgenSupport.emitLabelDef(labelCaseEnd, s);
	s.println("\t# end case");
    }

    public int countActiveVariables(){
	int result = this.expr.countActiveVariables();

	for(int i = 0; i<this.cases.getLength(); ++i){
    	    branch b = (branch)this.cases.getNth(i);	    
    	    int n = b.countActiveVariables();
	    if(n>result){
		result = n;
	    }
	}

	return result;
    }
}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    public Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start {} block");
	for(int i = 0; i<this.body.getLength(); ++i){    
            Expression e = (Expression)this.body.getNth(i);
	    e.code(s);
	}
	s.println("\t# end {} block");
    }

    public int countActiveVariables(){
	int result = 0;
	for(int i = 0; i<this.body.getLength(); ++i){
            Expression e = (Expression)this.body.getNth(i);
	    int n = e.countActiveVariables();
	    if(n>result){
		result = n;
	    }
	}

	return result;
    }
}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    public AbstractSymbol identifier;
    public AbstractSymbol type_decl;
    public Expression init;
    public Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
	dump_AbstractSymbol(out, n + 2, identifier);
	dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# let start");
	CgenScope.enterScope();
	CgenScope.addObject(this.identifier);

	if(!(this.init instanceof no_expr)){
	    s.println("\t# evaluate init block");
	    this.init.code(s);
	}else{
	    s.println("\t# init block is empty, use default value");
	    // TODO: If there is no initialization, the variable is initialized to the default value of T1
	    if(this.type_decl == TreeConstants.Int){
		CgenSupport.emitLoadInt(CgenSupport.ACC,
                                (IntSymbol)AbstractTable.inttable.lookup("0"), s);
	    }else if(this.type_decl == TreeConstants.Bool){
		CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
	    }else if(this.type_decl == TreeConstants.Str){
		CgenSupport.emitLoadString(CgenSupport.ACC,
                                   (StringSymbol)AbstractTable.stringtable.lookup(""), s);
	    }else {
		CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
	    }
	}

	s.println("\t# assign value from $a0 to " + this.identifier);
	ObjectDescription desc = CgenScope.getObjectDescription(this.identifier);
	//load expression value to $a0
	CgenSupport.emitStore(CgenSupport.ACC, -desc.offset,CgenSupport.FP, s);
	
	s.println("\t# evaluate body of let");
	this.body.code(s);	

	CgenScope.exitScope();
	s.println("\t#let end");
    }

    public int countActiveVariables(){
	int ninit = this.init.countActiveVariables();
	int nbody = this.body.countActiveVariables() + 1;//one variable is declared by the let itself
	if(ninit>nbody){
	    return ninit;
	}else{
	    return nbody;
	}
    }
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# calculate first argument of +");
	this.e1.code(s);
	s.println("\t# strore first argument of + in stack and calculate second argument");
	CgenSupport.emitPush(CgenSupport.ACC, s);// store first argument in stack
	this.e2.code(s);
	s.println("\t# clone second argument, this object will be used as a result and load its int value to t1");
	CgenSupport.emitJal("Object.copy", s);
	//load value from second argument object to $t1
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# restore first argument and load its int value to t2");
	CgenSupport.emitLoad(CgenSupport.T2, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T2, s);
	s.println("\t#perform main operation");
	CgenSupport.emitAdd(CgenSupport.T1, CgenSupport.T1, CgenSupport.T2, s);//perform the main operation
	s.println("\t# store value in the result object");
	CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# end of +");
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# calculate first argument of -");
	this.e1.code(s);
	s.println("\t# strore first argument of - in stack and calculate second argument");
	CgenSupport.emitPush(CgenSupport.ACC, s);// store first argument in stack
	this.e2.code(s);
	s.println("\t# clone second argument, this object will be used as a result and load its int value to t1");
	CgenSupport.emitJal("Object.copy", s);
	//load value from second argument object to $t1
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# restore first argument and load its int value to t2");
	CgenSupport.emitLoad(CgenSupport.T2, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T2, s);
	s.println("\t#perform main operation");
	CgenSupport.emitSub(CgenSupport.T1, CgenSupport.T2, CgenSupport.T1, s);//perform the main operation
	s.println("\t# store value in the result object");
	CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# end of -");
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# calculate first argument of *");
	this.e1.code(s);
	s.println("\t# strore first argument of * in stack and calculate second argument");
	CgenSupport.emitPush(CgenSupport.ACC, s);// store first argument in stack
	this.e2.code(s);
	s.println("\t# clone second argument, this object will be used as a result and load its int value to t1");
	CgenSupport.emitJal("Object.copy", s);
	//load value from second argument object to $t1
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# restore first argument and load its int value to t2");
	CgenSupport.emitLoad(CgenSupport.T2, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T2, s);
	s.println("\t#perform main operation");
	CgenSupport.emitMul(CgenSupport.T1, CgenSupport.T2, CgenSupport.T1, s);//perform the main operation
	s.println("\t# store value in the result object");
	CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# end of *");
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# calculate first argument of /");
	this.e1.code(s);
	s.println("\t# strore first argument of / in stack and calculate second argument");
	CgenSupport.emitPush(CgenSupport.ACC, s);// store first argument in stack
	this.e2.code(s);
	s.println("\t# clone second argument, this object will be used as a result and load its int value to t1");
	CgenSupport.emitJal("Object.copy", s);
	//load value from second argument object to $t1
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# restore first argument and load its int value to t2");
	CgenSupport.emitLoad(CgenSupport.T2, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T2, s);
	s.println("\t#perform main operation");
	CgenSupport.emitDiv(CgenSupport.T1, CgenSupport.T2, CgenSupport.T1, s);//perform the main operation
	s.println("\t# store value in the result object");
	CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# end of /");
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    public Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# calculate argument of ~");
	this.e1.code(s);
	s.println("\t# clone argument, this object will be used as a result and load its int value to t1");
	CgenSupport.emitJal("Object.copy", s);
	//load value from second argument object to $t1
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# perform main operation");
	CgenSupport.emitNeg(CgenSupport.T1, CgenSupport.T1, s);
	s.println("\t# store value in the result object");
	CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);
	s.println("\t# end of ~");
    }

    public int countActiveVariables(){
	return this.e1.countActiveVariables();
    }
}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start <. Evaluate first expression");
	this.e1.code(s);
	s.println("\t# put first expression to stack and evaluate second expression");
	CgenSupport.emitPush(CgenSupport.ACC, s);
	this.e2.code(s);
	s.println("\t# load int value of second expression to $t2");
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);

	s.println("\t# restore first exression result and load its int value to t1");
	CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T1, s);

	s.println("\t# this code assumes that e1<e2");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);

	int label = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBlt(CgenSupport.T1, CgenSupport.T2, label, s);
	s.println("\t# this code executes if e1>e2");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);

	CgenSupport.emitLabelDef(label, s);
	s.println("\t# end of <");
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start =. Evaluate first expression");
	this.e1.code(s);
	s.println("\t# put first expression to stack and evaluate second expression");
	CgenSupport.emitPush(CgenSupport.ACC, s);
	this.e2.code(s);
	s.println("\t# load second exression result to $t2");
	CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
	s.println("\t# restore first exression result to $t1");
	CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	
	s.println("\t# this code assumes that e1=e2");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);

	s.println("\t# compare object pointers");
	int labelEnd = CgenContext.labelNumber;
	CgenContext.labelNumber++;	
	CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, labelEnd, s);

	s.println("\t# if we reach here, pointers are not equal. Try to compare values");
	s.println("\t# if they are equal,the value in $a0 is returned, otherwise $a1 is returned.");
	CgenSupport.emitLoadBool(CgenSupport.A1, new BoolConst(false), s);
	CgenSupport.emitJal("equality_test", s);
	
	CgenSupport.emitLabelDef(labelEnd, s);
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    public Expression e1;
    public Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start <=. Evaluate first expression");
	this.e1.code(s);
	s.println("\t# put first expression to stack and evaluate second expression");
	CgenSupport.emitPush(CgenSupport.ACC, s);
	this.e2.code(s);
	s.println("\t# load int value of second expression to $t2");
	CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);

	s.println("\t# restore first exression result and load its int value to t1");
	CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.SP, s);//load first argument from stack
	CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T1, s);

	s.println("\t# this code assumes that e1<=e2");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);

	int label = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBleq(CgenSupport.T1, CgenSupport.T2, label, s);
	s.println("\t# this code executes if e1>e2");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);

	CgenSupport.emitLabelDef(label, s);
	s.println("\t# end of <=");
    }

    public int countActiveVariables(){
	int n1 = this.e1.countActiveVariables();
	int n2 = this.e2.countActiveVariables();
	if(n1>n2){
	    return n1;
	}else{
	    return n2;
	}
    }
}


/** Defines AST constructor 'comp'. (NOT in cool)
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    public Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start NOT. Evaluate expression");
	this.e1.code(s);
	s.println("\t# load value of a Bool object to $t1");
	CgenSupport.emitLoad(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s);

	s.println("\t# this code assumes that expression is true");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);

	s.println("\t# checking expression result value");
	int label = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBne(CgenSupport.T1, CgenSupport.ZERO, label, s);

	s.println("\t# this code executes if expression is false");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);

	CgenSupport.emitLabelDef(label, s);
	s.println("\t# end of NOT");
    }

    public int countActiveVariables(){
	return this.e1.countActiveVariables();
    }
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    public AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	CgenSupport.emitLoadInt(CgenSupport.ACC,
                                (IntSymbol)AbstractTable.inttable.lookup(token.getString()), s);
    }

    public int countActiveVariables(){
	return 0;
    }
}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    public Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(val), s);
    }

    public int countActiveVariables(){
	return 0;
    }
}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    public AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method method is provided
      * to you as an example of code generation.
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	CgenSupport.emitLoadString(CgenSupport.ACC,
                                   (StringSymbol)AbstractTable.stringtable.lookup(token.getString()), s);
    }

    public int countActiveVariables(){
	return 0;
    }
}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    public AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start new " + type_name); 

	if(this.type_name == TreeConstants.SELF_TYPE){
	    CgenSupport.emitLoad(CgenSupport.ACC, -CgenContext.selfObjectOffset, CgenSupport.FP, s);//load self to $a0
	    //load seft class number to $a0
	    CgenSupport.emitLoad(CgenSupport.ACC, CgenSupport.TAG_OFFSET, CgenSupport.ACC, s);
	    //load object table to t1
	    CgenSupport.emitLoadAddress(CgenSupport.T1, CgenSupport.CLASSOBJTAB, s);
	    //address of proto objects has offset 2*$a0 from t1
	    //multiply $a0 by 2 by shifting and store in $a0
	    CgenSupport.emitSll(CgenSupport.ACC, CgenSupport.ACC, 1, s);
	    //load address of proto object to $s0 by addit address of the object table and offset
	    CgenSupport.emitAddu(CgenSupport.SELF, CgenSupport.ACC, CgenSupport.T1, s);
	    //load proto object to $a0
	    CgenSupport.emitLoad(CgenSupport.ACC, 0, CgenSupport.SELF, s);
	    //clone proto object
	    CgenSupport.emitJal("Object.copy", s);
	    //store new object to stack
	    CgenSupport.emitPush(CgenSupport.ACC, s);
	    
	    //now we need to init attribues
	    //hope thant Object.copy didn't override $s0 and it still contains address of the proto object
	    //address of the init method is on offset 1 from proto object. Load init method label to $t1
	    CgenSupport.emitLoad(CgenSupport.T1, 1, CgenSupport.SELF, s);
	    //give control to object initializer
	    CgenSupport.emitJalr(CgenSupport.T1, s);

	    //restore object from stack to $a0
	    CgenSupport.emitLoad(CgenSupport.ACC, 0, CgenSupport.SP, s);
	    CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	}else{
	    //load proto object to a0
	    CgenSupport.emitLoadAddress(CgenSupport.ACC, this.type_name + CgenSupport.PROTOBJ_SUFFIX,s);
	    //clone proto object
	    CgenSupport.emitJal("Object.copy", s);
	    //store new object to stack
	    CgenSupport.emitPush(CgenSupport.ACC, s);
    	    //call init			
	    CgenSupport.emitJal(this.type_name + CgenSupport.CLASSINIT_SUFFIX, s);
	    //restore object from stack to $a0
	    CgenSupport.emitLoad(CgenSupport.ACC, 0, CgenSupport.SP, s);
	    CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, CgenSupport.WORD_SIZE, s);
	}

	s.println("\t# finish new");
    }

    public int countActiveVariables(){
	return 0;
    }
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    public Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t# start isvoid. Evaluate expression");
	this.e1.code(s);
	s.println("\t# copy expression value to $t1");
	CgenSupport.emitMove(CgenSupport.T1, CgenSupport.ACC, s);

	s.println("\t# this code assumes that expression is not void");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(false), s);
	
	//check for void
	s.println("\t# checking  object for void");
	int label = CgenContext.labelNumber;
	CgenContext.labelNumber++;
	CgenSupport.emitBne(CgenSupport.T1, CgenSupport.ZERO, label, s);

	s.println("\t# this code executes if expression is void");
	CgenSupport.emitLoadBool(CgenSupport.ACC, new BoolConst(true), s);

	CgenSupport.emitLabelDef(label, s);
	s.println("\t# end isvoid");
    }

    public int countActiveVariables(){
	return this.e1.countActiveVariables();
    }
}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	//no code for empty expression
    }

    public int countActiveVariables(){
	return 0;
    }
}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    public AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
	dump_AbstractSymbol(out, n + 2, name);
	dump_type(out, n);
    }
    /** Generates code for this expression.  This method is to be completed 
      * in programming assignment 5.  (You may add or remove parameters as
      * you wish.)
      * @param s the output stream 
      * */
    public void code(PrintStream s) {
	s.println("\t#start object ref for " + this.name);
	if(this.name == TreeConstants.self){
	    CgenSupport.emitLoad(CgenSupport.ACC, -CgenContext.selfObjectOffset, CgenSupport.FP, s);//load self to $a0
	    return;
	}
	
	ObjectDescription desc = CgenScope.getObjectDescription(this.name);
	if(desc.isAttribute){
	    s.println("\t# object is an attribute, it is located in the self object");
	    //attributes are located in the self object starting with offset 3
	    CgenSupport.emitLoad(CgenSupport.T1, -CgenContext.selfObjectOffset, CgenSupport.FP, s);//load self to $t1
	    CgenSupport.emitLoad(CgenSupport.ACC, desc.offset + CgenSupport.DEFAULT_OBJFIELDS, 
		CgenSupport.T1, s);//load attribute to $a0
	}else{
	    s.println("\t# object is a formal parameter or inner variable");
	    //formal parameters and variables are located in the frame
	    CgenSupport.emitLoad(CgenSupport.ACC, -desc.offset, CgenSupport.FP, s);//load to $a0
	}
	s.println("\t#end object ref for " + this.name);
    }

    public int countActiveVariables(){
	return 0;
    }
}

class ObjectDescription{
    public AbstractSymbol name;
    //attributes are offset relatime to self, other variables - relative to frame pointer
    public boolean isAttribute;
    public int offset;
}

class CgenScope {
    //order number of an object, points to the next free memory slot
    private static int objectOffset;
    private static SymbolTable objects;
    //write an error message if there is more frame objects then max
    private static int maxObjectsCount;

    public static void init(){
	objects = new SymbolTable();
	maxObjectsCount = -1;
	objectOffset = 0;
    }

    public static void addAttributes(CgenNode classInstance){
	objects.enterScope();
	List<AttributeDescription> attributes = classInstance.getAttributes();
	for(int i=0;i<attributes.size();++i){
	    AttributeDescription attribute = attributes.get(i);
	    ObjectDescription desc = new ObjectDescription();
	    desc.name = attribute.name;
	    desc.isAttribute = true;
	    desc.offset = i;

	    objects.addId(attribute.name, desc);
	}
    }
    public static void addFormals(method m){
	for (Enumeration e = m.formals.getElements(); e.hasMoreElements();) {

	    ObjectDescription desc = new ObjectDescription();
	    formal f = (formal)e.nextElement();
	    desc.name = f.name;
	    desc.isAttribute = false;
	    desc.offset = objectOffset;
    
	    objects.addId(desc.name, desc);

	    if(maxObjectsCount >=0 && objectOffset >= maxObjectsCount){
		throw new IllegalArgumentException("expect " + maxObjectsCount + " variables in scope, but has more " 
		    + m.getName());
	    }

	    objectOffset++;
        }
    }

    public static void addObject(AbstractSymbol name){
	ObjectDescription desc = new ObjectDescription();
	desc.name = name;
	desc.isAttribute = false;
	desc.offset = objectOffset;
    
	objects.addId(desc.name, desc);

	if(maxObjectsCount >=0 && objectOffset >= maxObjectsCount){
	    throw new IllegalArgumentException("expect " + maxObjectsCount + " variables in scope, but has more "
		+ name);
	}

	objectOffset++;
    }

    public static ObjectDescription getObjectDescription(AbstractSymbol name){
	return (ObjectDescription)objects.lookup(name);
    }

    public static void enterScope(){
	objects.enterScope();
    }
    
    public static void exitScope(){
	// object slots from the scope can be reused
	objectOffset -= objects.getTopScopeSize();
	objects.exitScope();
    }

    public static void setMaxObjectsCount(int count){
	maxObjectsCount = count;
    }
}

class CgenContext{
    //offset of the self object relative to frame pointer in stack
    public static int selfObjectOffset;
    //global label counter, point to next unused label number
    public static int labelNumber = 0;
    public static CgenClassTable classTable;
    public static AbstractSymbol currentClassName;
    public static CgenNode currentClass;
}