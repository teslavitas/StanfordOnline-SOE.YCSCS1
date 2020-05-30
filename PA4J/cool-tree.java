// -*- mode: java -*- 
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

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

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
    public abstract void semant();
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


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
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

	classTable.semant();
	/* some semantic analysis code may go here */
	
	if (classTable.errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
	}

	SemantScope.init(classTable);
	for(Enumeration e = classes.getElements();e.hasMoreElements();){
	    class_c c = (class_c)e.nextElement();
	    c.semant();
	}

	if(SemantScope.hasErrors()){
	    System.exit(1);
	}
	//System.exit(1);//TODO: remove this
    }

}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public AbstractSymbol getFilename() { return filename; }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public Features getFeatures() { return features; }

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

    public void semant() {
	SemantScope.enterClass(this.name);

	for(Enumeration e = this.features.getElements();e.hasMoreElements();){
	    Feature f = (Feature)e.nextElement();
	    if(f instanceof method){
		method m = (method)f;
		m.semant();
	    }
	    if(f instanceof attr){
		attr a = (attr)f;
		a.semant();
	    }
	}
	
	SemantScope.exitClass();
    }
}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
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
    public Formals getFormals() { return this.formals; }
    public AbstractSymbol getReturnType() { return this.return_type; }

    public void semant() {
	SemantScope.enterScope();
	for(int i = 0; i<this.formals.getLength(); ++i){
	    formalc formal = (formalc)this.formals.getNth(i);
	    if(SemantScope.probe(formal.getName())){
		SemantScope.trackError(this, "Duplicated argument " + formal.getName() + " in method " + this.name);
	    }
	    if(formal.getTypeName() == TreeConstants.SELF_TYPE){
		SemantScope.trackError(this, "argument "+ formal.getName() + " of method " + this.name 
		    + "has type SELF_TYPE, which is not allowed");
	    }
	    if(formal.getName() == TreeConstants.self){
		SemantScope.trackError(this, "Method " + this.name + " has an arument self, this name is not allowed");
	    }
	    SemantScope.addVariable(formal.getName(), formal.getTypeName());
	}

	this.expr.semant();
	if(!SemantScope.classTable.isSubType(this.expr.get_type(), this.return_type, SemantScope.getCurrClass())){
	    SemantScope.trackError(this, "Method " + this.name + " returns " + this.expr.get_type() 
		+ " but should return " + this.return_type + " or its subtype");
	}
	SemantScope.exitScope();
    }
}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
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
    public AbstractSymbol getReturnType() { return this.type_decl; }

    public void semant() {
	init.semant();
	if(!SemantScope.classTable.isSubType(this.init.get_type(), this.type_decl, SemantScope.getCurrClass())){
	    SemantScope.trackError(this, "Initialization expression for attribute" + this.name + " has type " 
		+ this.type_decl +  " but is assigned with value of " + this.init.get_type());
	}
    }
}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }
    public AbstractSymbol getName() { return this.name; }
    public AbstractSymbol getTypeName() { return this.type_decl; }
}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    public AbstractSymbol type_decl;
    protected Expression expr;
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

    public void semant(){
	SemantScope.enterScope();
	SemantScope.addVariable(this.name, this.type_decl);
	this.expr.semant();
	SemantScope.exitScope();
    }
    public AbstractSymbol get_type(){
	return this.expr.get_type();
    }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
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
    
    public void semant(){
	this.expr.semant();
	AbstractSymbol variable = SemantScope.lookupVariable(this.name);
	if(variable == null){
	    SemantScope.trackError(this, "Assignment to not declared variable " + this.name);
	} else if(!SemantScope.classTable.isSubType(this.expr.get_type(), variable, SemantScope.getCurrClass())){
	    SemantScope.trackError(this, "Variable " + this.name + " has type " + variable 
		+ " but is assigned with value of " + this.expr.get_type());
	}

	this.set_type(this.expr.get_type());
    }
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
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

    public void semant(){
	if(this.type_name == TreeConstants.SELF_TYPE){
	    SemantScope.trackError(this, "Static dispatch to SELF_TYPE");
	    this.set_type(TreeConstants.Object_);
	    return;
	}

	expr.semant();
	if(!SemantScope.classTable.isSubType(this.expr.get_type(), this.type_name, SemantScope.getCurrClass())){
	    SemantScope.trackError(this, "Type of static dispatch caller " + this.expr.get_type() + " is not a subtype of "
		+this.type_name);
	}

	List<AbstractSymbol> formalTypes = SemantScope.classTable.getMethod(this.type_name, this.name);
	if(formalTypes == null){
	    SemantScope.trackError(this, "Cannot find method " + this.name + " in class " + this.type_name);
	    this.set_type(TreeConstants.Object_);
	    return;
	}

	boolean paramsCountOk = true;

	//last item in formal list is method's return type, so it should be 1 element longer than list of actuals
	if(this.actual.getLength() != formalTypes.size() - 1){
	    SemantScope.trackError(this, "expect to have " + (formalTypes.size() - 1) + " parameters in method "
		+ this.name + ", but has " + this.actual.getLength());
	    paramsCountOk = false;
	}

	if(paramsCountOk){
	for(int i = 0; i<this.actual.getLength(); ++i){
	    Expression e = (Expression)this.actual.getNth(i);
	    e.semant();
	    AbstractSymbol formalType = formalTypes.get(i);
	    if(!SemantScope.classTable.isSubType(e.get_type(), formalType, SemantScope.getCurrClass())){
		SemantScope.trackError(this, "argument number " + i + " of method " + this.name + " has type " 
		    + e.get_type() + ", but should be " + formalType + " or its subtype");
	    }
	}
	}

	AbstractSymbol resultType = formalTypes.get(formalTypes.size()-1);
	if(resultType == TreeConstants.SELF_TYPE){
	    resultType = this.expr.get_type();
	}
	this.set_type(resultType);
    }

}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
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

    public void semant(){
	expr.semant();
	AbstractSymbol caller = expr.get_type();
	if(caller == TreeConstants.SELF_TYPE){
	    caller = SemantScope.getCurrClass();
	}

	List<AbstractSymbol> formalTypes = SemantScope.classTable.getMethod(caller, this.name);
	if(formalTypes == null){
	    SemantScope.trackError(this, "Cannot find method " + this.name + " in class " + caller);
	    this.set_type(TreeConstants.Object_);
	    return;
	}

	boolean paramsCountOk = true;

	//last item in formal list is method's return type, so it should be 1 element longer than list of actuals
	if(this.actual.getLength() != formalTypes.size() - 1){
	    SemantScope.trackError(this, "expect to have " + (formalTypes.size() - 1) + " parameters in method "
		+ this.name + ", but has " + this.actual.getLength());
	    paramsCountOk = false;
	}

	if(paramsCountOk){
	for(int i = 0; i<this.actual.getLength(); ++i){
	    Expression e = (Expression)this.actual.getNth(i);
	    e.semant();
	    AbstractSymbol formalType = formalTypes.get(i);
	    if(!SemantScope.classTable.isSubType(e.get_type(), formalType, SemantScope.getCurrClass())){
		SemantScope.trackError(this, "argument number " + i + " of method " + this.name + " has type " 
		    + e.get_type() + ", but should be " + formalType + " or its subtype");
	    }
	}
	}

	AbstractSymbol resultType = formalTypes.get(formalTypes.size()-1);
	if(resultType == TreeConstants.SELF_TYPE){
	    resultType = expr.get_type();
	}
	this.set_type(resultType);
    }
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
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

    public void semant(){
	this.pred.semant();
	this.then_exp.semant();
	this.else_exp.semant();

	if(pred.get_type() != TreeConstants.Bool){
	    SemantScope.trackError(this, "If condition has type " + this.pred.get_type() +", but should be bool");
	}

	AbstractSymbol resultType = SemantScope.classTable.getCommonType(
	    this.then_exp.get_type(),this.else_exp.get_type(), SemantScope.getCurrClass());
	this.set_type(resultType);
    }
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
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

    public void semant(){
	this.pred.semant();
	if(this.pred.get_type() != TreeConstants.Bool){
	    SemantScope.trackError(this, "loop condition has type " + this.pred.get_type() +", but should be bool");
	}
	this.body.semant();
	this.set_type(TreeConstants.Object_);
    }
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
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

    public void semant(){
	this.expr.semant();

	List<AbstractSymbol> types = new ArrayList<AbstractSymbol>();
	AbstractSymbol resultType = null;

	for(int i = 0; i<this.cases.getLength(); ++i){
	    branch b = (branch)this.cases.getNth(i);	    
	    b.semant();
	    if(types.contains(b.type_decl)){
		SemantScope.trackError(this, "multiple branches with variable type " + b.type_decl + " in CASE expression");
	    } else if(b.type_decl == TreeConstants.SELF_TYPE){
		SemantScope.trackError(this, "variables with type SELF_TYPE are not allowed  in CASE expression");
	    }
	    types.add(b.type_decl);
	    if(resultType == null){
		resultType = b.get_type();
	    }else{
		resultType = SemantScope.classTable.getCommonType(resultType, b.get_type(), SemantScope.getCurrClass());
	    }
	}

	this.set_type(resultType);
    }
}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
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

    public void semant(){
	AbstractSymbol lastType = null;
	for(int i = 0; i<this.body.getLength(); ++i){
	    Expression e = (Expression)this.body.getNth(i);
	    e.semant();
	    lastType = e.get_type();
	}
	this.set_type(lastType);
    }
}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
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

    public void semant(){
	this.init.semant();
	boolean isOk = true;

	if(this.init.get_type() != TreeConstants.No_type){// if there is no init value, its type will be No_type
	    if(!SemantScope.classTable.isSubType(this.init.get_type(), this.type_decl, SemantScope.getCurrClass())){
		SemantScope.trackError(this, "init expression of let has type " + this.init.get_type() +
		    " but should be " + this.type_decl + " or its subtype");
		isOk = false;
    	    }
	}
	
	SemantScope.enterScope();
	SemantScope.addVariable(this.identifier, this.type_decl);
	this.body.semant();
	SemantScope.exitScope();
	
	//if(isOk){
	    this.set_type(this.body.get_type());
	//}else{
	//    this.set_type(TreeConstants.Object_);
	//}
    }
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "first operand of + should be Int, but is " + this.e1.get_type());
	}
	if(this.e2.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "second of + should be Int, but is " + this.e1.get_type());
	}

        this.set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "first operand of - should be Int, but is " + this.e1.get_type());
	}
	if(this.e2.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "second of - should be Int, but is " + this.e1.get_type());
	}

        this.set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "first operand of * should be Int, but is " + this.e1.get_type());
	}
	if(this.e2.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "second of * should be Int, but is " + this.e1.get_type());
	}

        this.set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'divide'. /
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "first operand of / should be Int, but is " + this.e1.get_type());
	}
	if(this.e2.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "second of / should be Int, but is " + this.e1.get_type());
	}

        this.set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'neg'. (-Int operator)
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
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

    public void semant(){
	this.e1.semant();
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    SemantScope.trackError(this, "operand of - should be Int, but is " + this.e1.get_type());
	}

        this.set_type(TreeConstants.Bool);
    }
}


/** Defines AST constructor 'lt'. <
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();

	boolean isOk = true;
	// we don't need to check subtypes of Int because it is not allowed to inherit from Int
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    isOk = false;
	    SemantScope.trackError(this, "first operand of < should be Int, but is " + this.e1.get_type());
	}
	if(this.e2.get_type() != TreeConstants.Int){
	    
	    isOk = false;
	    SemantScope.trackError(this, "second operand of < should be Int, but is " + this.e2.get_type());
	}

	//if(isOk){
	    this.set_type(TreeConstants.Bool);
	//}
    }
}


/** Defines AST constructor 'eq'. =
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();
	
	if(this.e1.get_type() == TreeConstants.Int && this.e2.get_type() != TreeConstants.Int){
    	    SemantScope.trackError(this, "first operand of = is Int, but second is " + this.e2.get_type());
	} else if(this.e2.get_type() == TreeConstants.Int && this.e1.get_type() != TreeConstants.Int){
    	    SemantScope.trackError(this, "second operand of = is Int, but first is " + this.e2.get_type());
	} else if(this.e1.get_type() == TreeConstants.Bool && this.e2.get_type() != TreeConstants.Bool){
    	    SemantScope.trackError(this, "first operand of = is Bool, but second is " + this.e2.get_type());
	} else if(this.e2.get_type() == TreeConstants.Bool && this.e1.get_type() != TreeConstants.Bool){
    	    SemantScope.trackError(this, "second operand of = is Bool, but first is " + this.e2.get_type());
	} else if(this.e1.get_type() == TreeConstants.Str && this.e2.get_type() != TreeConstants.Str){
    	    SemantScope.trackError(this, "first operand of = is String, but second is " + this.e2.get_type());
	} else if(this.e2.get_type() == TreeConstants.Str && this.e1.get_type() != TreeConstants.Str){
    	    SemantScope.trackError(this, "second operand of = is String, but first is " + this.e2.get_type());
	}

	this.set_type(TreeConstants.Bool);
    }
}


/** Defines AST constructor 'leq'. <=
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
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

    public void semant(){
	this.e1.semant();
	this.e2.semant();

	boolean isOk = true;
	// we don't need to check subtypes of Int because it is not allowed to inherit from Int
	if(this.e1.get_type() != TreeConstants.Int){
	    
	    isOk = false;
	    SemantScope.trackError(this, "first operand of <= should be Int, but is " + this.e1.get_type());
	}
	if(this.e2.get_type() != TreeConstants.Int){
	    
	    isOk = false;
	    SemantScope.trackError(this, "second operand of <= should be Int, but is " + this.e2.get_type());
	}

	//if(isOk){
	    this.set_type(TreeConstants.Bool);
	//}
    }
}


/** Defines AST constructor 'comp' (NOT in cool)
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
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

    public void semant(){
	this.e1.semant();

	boolean isOk = true;
	// we don't need to check subtypes of Bool because it is not allowed to inherit from Bool
	if(this.e1.get_type() != TreeConstants.Bool){
	    
	    isOk = false;
	    SemantScope.trackError(this, "Operand of NOT should be Bool, but is " + this.e1.get_type());
	}

	//if(isOk){
	    this.set_type(TreeConstants.Bool);
	//}
    }
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
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

    public void semant(){
	this.set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
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

    public void semant(){
	this.set_type(TreeConstants.Bool);
    }
}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
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

    public void semant(){
	this.set_type(TreeConstants.Str);
    }
}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
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

    public void semant(){
	// this should work for both SELF_TYPE and regular types
	if(!SemantScope.classTable.hasClass(this.type_name) && this.type_name != TreeConstants.SELF_TYPE){
	    SemantScope.trackError(this, "Call to NEW with non-existing class " + this.type_name);
	    this.set_type(TreeConstants.Object_);
	}else{
	    this.set_type(this.type_name);
	}
    }
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
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

    public void semant(){
	e1.semant();
	this.set_type(TreeConstants.Bool);	
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

    public void semant(){
	this.set_type(TreeConstants.No_type);	
    }
}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
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

    public void semant(){
	if(this.name == TreeConstants.self){
	    this.set_type(TreeConstants.SELF_TYPE);
	}else{
	    AbstractSymbol type = SemantScope.lookupVariable(this.name);
	    if(type == null){
		SemantScope.trackError(this, this.name + " not found");
		this.set_type(TreeConstants.Object_);
	    }else{
		this.set_type(type);
	    }
	}
    }
}

class SemantScope {
    private static SymbolTable classes;
    private static SymbolTable objects;
    public static ClassTable classTable;
    //reference to current class
    private static AbstractSymbol currClass;
    private static int errorCount;

    public static void init(ClassTable classTable){
	SemantScope.classTable = classTable;
	SemantScope.classes = new SymbolTable();
	SemantScope.objects = new SymbolTable();
	currClass = AbstractTable.stringtable.addString("CurrClass12345678");
	errorCount = 0;
    }

    public static void enterClass(AbstractSymbol className){
	SemantScope.classes.enterScope();
	classes.addId(currClass, className);
    }

    public static void exitClass()
    {
	classes.exitScope();
    }

    public static AbstractSymbol lookupVariable(AbstractSymbol name){
	Object fromObjectScope = objects.lookup(name);
	if(fromObjectScope != null){
	    return (AbstractSymbol)fromObjectScope;
	}

	AbstractSymbol className = (AbstractSymbol)classes.lookup(currClass);
	AbstractSymbol fromMethod = classTable.getAttribute(className, name);

	return fromMethod;
    }

    public static void enterScope(){
	objects.enterScope();
    }

    public static void exitScope(){
	objects.exitScope();
    }

    public static void addVariable(AbstractSymbol name, AbstractSymbol type){
	objects.addId(name, type);
    }

    public static boolean probe(AbstractSymbol name){
	return objects.probe(name) != null;
    }

    public static AbstractSymbol getCurrClass(){
	return (AbstractSymbol)classes.lookup(currClass);
    }

    public static void trackError(TreeNode node, String error){
	errorCount++;
	AbstractSymbol className = (AbstractSymbol)classes.lookup(currClass);
	class_c classObject = classTable.getClass(className);
	classTable.semantError(classObject.getFilename(), node);
	System.err.println(error);	
    }

    public static boolean hasErrors(){
	return errorCount > 0;
    }
}
