package com.ibm.wala.cast.python.test;

import java.io.IOException;
import java.util.Set;

import com.ibm.wala.cast.python.client.PythonAnalysisEngine;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.codeBreaker.turtle.PythonTurtleAnalysisEngine.EdgeType;
import com.ibm.wala.codeBreaker.turtle.PythonTurtleAnalysisEngine.TurtlePath;
import com.ibm.wala.codeBreaker.turtle.PythonTurtleSKLearnClassifierAnalysis;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.HashSetFactory;
import com.ibm.wala.util.graph.Graph;
import com.ibm.wala.util.graph.labeled.NumberedLabeledGraph;

public class TestPythonTurtleSKLearnClassifierCallGraphShape extends TestPythonTurtleCallGraphShape {

	public TestPythonTurtleSKLearnClassifierCallGraphShape() {
		super(false);
	}

	@Override
	protected PythonAnalysisEngine<NumberedLabeledGraph<TurtlePath, EdgeType>> makeEngine(String... name) throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
		PythonAnalysisEngine<NumberedLabeledGraph<TurtlePath, EdgeType>> engine = new PythonTurtleSKLearnClassifierAnalysis();
		Set<Module> modules = HashSetFactory.make();
		for(String n : name) {
			modules.add(getScript(n));
		}
		engine.setModuleFiles(modules);
		return engine;
	}

	public static void main(String[] args) throws IllegalArgumentException, ClassHierarchyException, CancelException, IOException {
		TestPythonTurtleSKLearnClassifierCallGraphShape driver = new TestPythonTurtleSKLearnClassifierCallGraphShape();
		PythonAnalysisEngine<NumberedLabeledGraph<TurtlePath, EdgeType>> E = driver.makeEngine(args);

		CallGraphBuilder<? super InstanceKey> builder = E.defaultCallGraphBuilder();
		CallGraph CG = builder.makeCallGraph(E.getOptions(), new NullProgressMonitor());

		Graph<TurtlePath> analysis = E.performAnalysis((SSAPropagationCallGraphBuilder)builder);
	}
	
}
