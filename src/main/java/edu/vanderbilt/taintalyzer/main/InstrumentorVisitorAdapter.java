package edu.vanderbilt.taintalyzer.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Stack;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsStmt;
import com.github.javaparser.ast.modules.ModuleOpensStmt;
import com.github.javaparser.ast.modules.ModuleProvidesStmt;
import com.github.javaparser.ast.modules.ModuleRequiresStmt;
import com.github.javaparser.ast.modules.ModuleUsesStmt;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class InstrumentorVisitorAdapter<A> implements VoidVisitor<A> {

	private String Current_method_name;
	private final CompilationUnit cu;
	private Stack<String> parent_loop_label = new Stack<String>();
	private Stack<Integer> loop_counter = new Stack<Integer>();
	private final String Path;
	private static HashSet<String> All_vars = new HashSet<String>();

	public InstrumentorVisitorAdapter(CompilationUnit cu, String Path) {
		// TODO Auto-generated constructor stub
		this.loop_counter.push(0);
		this.parent_loop_label.push("");
		this.Path = Path;
		this.cu = cu;
		cu.accept(new VariableCollector(), InstrumentorVisitorAdapter.All_vars);
	}
	
	public void startInstrumenting()
	{
		this.visitDepthFirst(cu);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void visit(NodeList n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());
	}

	@Override
	public void visit(AnnotationDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(AnnotationMemberDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ArrayAccessExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ArrayCreationExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ArrayCreationLevel n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ArrayInitializerExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ArrayType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(AssertStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(AssignExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(BinaryExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(BlockComment n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(BlockStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(BooleanLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(BreakStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(CastExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(CatchClause n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(CharLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ClassExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ClassOrInterfaceType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(CompilationUnit n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ConditionalExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ConstructorDeclaration n, A arg) {
		this.Current_method_name = n.getNameAsString();
		this.loop_counter.push(0);
		this.parent_loop_label.push(this.Current_method_name);

	}

	@Override
	public void visit(ContinueStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(DoStmt n, A arg) {
		Integer lc = this.loop_counter.peek() + 1;
		String pn = new String(this.parent_loop_label.peek() + "_L" + lc);
		this.loop_counter.pop();
		this.loop_counter.push(lc);
		this.loop_counter.push(0);
		this.parent_loop_label.push(pn);

		Statement body = n.getBody();
		if (body instanceof ExpressionStmt)
			n.setBody(this.ExprStmtToBlockStmt((ExpressionStmt) body));

		HashSet<String> variables = new HashSet<String>();
		Expression while_condition = n.getCondition();

		if (while_condition instanceof BooleanLiteralExpr)
			System.out.println(while_condition.toString());
		// TODO Not sure what to do here
		else
			while_condition.accept(new WhileConditionAdapter(), variables);

		for (String var : variables) {
			if (InstrumentorVisitorAdapter.All_vars.contains(var))
				createIfStatement((BlockStmt) n.getBody(), var, pn);
		}

	}

	@Override
	public void visit(DoubleLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(EmptyStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(EnclosedExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(EnumConstantDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(EnumDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ExpressionStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(FieldAccessExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(FieldDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ForStmt n, A arg) {
		Integer lc = this.loop_counter.peek() + 1;
		String pn = new String(this.parent_loop_label.peek() + "_L" + lc);
		this.loop_counter.pop();
		this.loop_counter.push(lc);
		this.loop_counter.push(0);
		this.parent_loop_label.push(pn);

		Statement body = n.getBody();
		if (body instanceof ExpressionStmt)
			n.setBody(this.ExprStmtToBlockStmt((ExpressionStmt) body));

		ArrayList<String> variable_names = new ArrayList<>();
		Iterator<Expression> it = n.getInitialization().iterator();
		while (it.hasNext()) {
			Iterator<VariableDeclarator> it_1 = ((VariableDeclarationExpr) it.next()).getVariables().iterator();
			while (it_1.hasNext())
				variable_names.add(it_1.next().getNameAsString());
		}
		for (String var : variable_names) {

			createIfStatement((BlockStmt) n.getBody(), var, pn);
		}

	}

	@Override
	public void visit(ForeachStmt n, A arg) {
		Integer lc = this.loop_counter.peek() + 1;
		String pn = new String(this.parent_loop_label.peek() + "_L" + lc);
		this.loop_counter.pop();
		this.loop_counter.push(lc);
		this.loop_counter.push(0);
		this.parent_loop_label.push(pn);

		Statement body = n.getBody();
		if (body instanceof ExpressionStmt)
			n.setBody(this.ExprStmtToBlockStmt((ExpressionStmt) body));

		HashSet<String> variables = new HashSet<String>();
		n.getVariable().getVariables().forEach(var -> variables.add(var.getNameAsString()));

		for (String var : variables)
			createIfStatement((BlockStmt) n.getBody(), var, pn);

	}

	@Override
	public void visit(IfStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ImportDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(InitializerDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(InstanceOfExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(IntegerLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(IntersectionType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(JavadocComment n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(LabeledStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(LambdaExpr n, A arg) {
		if (n.getParentNode().isPresent()) {
			Node Parent_ = n.getParentNode().get();
			if ((Parent_ instanceof MethodCallExpr)
					&& (((MethodCallExpr) Parent_).getNameAsString().equals("forEach"))) {
				Integer lc = this.loop_counter.peek() + 1;
				String pn = new String(this.parent_loop_label.peek() + "_L" + lc);
				this.loop_counter.pop();
				this.loop_counter.push(lc);
				this.loop_counter.push(0);
				this.parent_loop_label.push(pn);

				Statement body = n.getBody();
				if (body instanceof ExpressionStmt)
					n.setBody(this.ExprStmtToBlockStmt((ExpressionStmt) body));
				n.getParameters().forEach(par -> createIfStatement((BlockStmt) n.getBody(), par.toString(), pn));
			} else {
				this.loop_counter.push(this.loop_counter.peek());
				this.parent_loop_label.push(this.parent_loop_label.peek());
			}
		}

		else {
			this.loop_counter.push(this.loop_counter.peek());
			this.parent_loop_label.push(this.parent_loop_label.peek());
		}

	}

	@Override
	public void visit(LineComment n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(LocalClassDeclarationStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(LongLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(MarkerAnnotationExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(MemberValuePair n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(MethodCallExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());
	}

	@Override
	public void visit(MethodDeclaration n, A arg) {
		this.Current_method_name = n.getNameAsString();
		this.loop_counter.push(0);
		this.parent_loop_label.push(this.Current_method_name);
		
		if (this.Current_method_name.equals("main")){
			String S1 = new String("JsonCreator.remove_json(\"" + this.Path +"\" )");
			Optional<BlockStmt> methodbody = n.getBody();
			if (methodbody.isPresent()){
				methodbody.get().addStatement(0, new NameExpr(S1));
			}
		}
	}

	@Override
	public void visit(MethodReferenceExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(NameExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(Name n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(NormalAnnotationExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(NullLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ObjectCreationExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(PackageDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(Parameter n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(PrimitiveType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ReturnStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(SimpleName n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(StringLiteralExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(SuperExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(SwitchEntryStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(SwitchStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(SynchronizedStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ThisExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ThrowStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(TryStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(TypeExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(TypeParameter n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(UnaryExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(UnionType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(UnknownType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(VariableDeclarationExpr n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(VariableDeclarator n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(VoidType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(WhileStmt n, A arg) {
		Integer lc = this.loop_counter.peek() + 1;
		String pn = new String(this.parent_loop_label.peek() + "_L" + lc);
		this.loop_counter.pop();
		this.loop_counter.push(lc);
		this.loop_counter.push(0);
		this.parent_loop_label.push(pn);

		Statement body = n.getBody();
		if (body instanceof ExpressionStmt)
			n.setBody(this.ExprStmtToBlockStmt((ExpressionStmt) body));

		HashSet<String> variables = new HashSet<String>();
		Expression while_condition = n.getCondition();

		if (while_condition instanceof BooleanLiteralExpr)
			System.out.println(while_condition.toString());
		// TODO: Not sure what to do in this case
		else
			while_condition.accept(new WhileConditionAdapter(), variables);

		for (String var : variables) {
			if (InstrumentorVisitorAdapter.All_vars.contains(var))
				createIfStatement((BlockStmt) n.getBody(), var, pn);
		}

	}

	@Override
	public void visit(WildcardType n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ModuleDeclaration n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ModuleRequiresStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ModuleExportsStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ModuleProvidesStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ModuleUsesStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(ModuleOpensStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	@Override
	public void visit(UnparsableStmt n, A arg) {
		this.loop_counter.push(this.loop_counter.peek());
		this.parent_loop_label.push(this.parent_loop_label.peek());

	}

	public void visitDepthFirst(Node node) {
		node.accept(this, null);
		new ArrayList<>(node.getChildNodes()).forEach(this::visitDepthFirst);
		this.loop_counter.pop();
		this.parent_loop_label.pop();
	}

	private void createIfStatement(BlockStmt body, String var_name, String parent_loop_name) {
		Expression exp = new BinaryExpr(new NameExpr("MultiTainter.getTaint(" + var_name + ")"), new NullLiteralExpr(),
				com.github.javaparser.ast.expr.BinaryExpr.Operator.NOT_EQUALS);
		String S1 = new String("TaintEntry T_" + var_name + " = new TaintEntry(\"" + parent_loop_name + "\", \""
				+ var_name + "\", MultiTainter.getTaint(" + var_name + ").getDependencies())");
		String S2 = new String("JsonCreator.append_json(\"" + this.Path + "\", " + "T_" + var_name + ")");
		BlockStmt B1 = new BlockStmt();
		B1.addStatement(new NameExpr(S1));
		B1.addStatement(new NameExpr(S2));
		Statement then_statement = B1;
		IfStmt if_ = new IfStmt(exp, then_statement, null);
		body.addStatement(if_);
	}

	private BlockStmt ExprStmtToBlockStmt(ExpressionStmt E) {
		BlockStmt S1 = new BlockStmt();
		S1.addStatement(E);
		return S1;
	}

	public static class WhileConditionAdapter extends VoidVisitorAdapter<HashSet<String>> {

		public void visit(final NameExpr n, HashSet<String> arg) {
			arg.add(n.toString());
			super.visit(n, arg);
		}

	}

	public static class VariableCollector extends VoidVisitorAdapter<HashSet<String>> {

		public void visit(final VariableDeclarationExpr n, HashSet<String> args) {
			n.getVariables().forEach(var -> args.add(var.getNameAsString()));

		}
	}

}
