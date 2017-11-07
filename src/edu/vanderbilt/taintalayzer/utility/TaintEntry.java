package edu.vanderbilt.taintalayzer.utility;

import edu.columbia.cs.psl.phosphor.struct.LinkedList;
import edu.columbia.cs.psl.phosphor.struct.LinkedList.Node;

import java.lang.StringBuilder;



public class TaintEntry {
	
	 public String getLoop_name_() {
		return loop_name_;
	}

	public void setLoop_name_(String loop_name_) {
		this.loop_name_ = loop_name_;
	}

	public String getLabel_() {
		return label_;
	}

	public void setLabel_(String label_) {
		this.label_ = label_;
	}

	public LinkedList<Object> getDependencies_() {
		return dependencies_;
	}

	public void setDependencies_(LinkedList<Object> dependencies_) {
		this.dependencies_ = dependencies_;
	}

	private String loop_name_ ;
	private String label_ ;
	private LinkedList<Object> dependencies_ ;
	
	public TaintEntry(String loop_name, String label, LinkedList<Object> fake_dependendencies)  {
		this.loop_name_ = loop_name;
		this.label_ = label;
		this.dependencies_ = fake_dependendencies;
		
		
	}
	
	public TaintEntry()
	{
		this.loop_name_ = new String();
		this.label_ = new String();
		this.dependencies_ = new LinkedList<Object>();
	}
	
	
	
	@Override
	public int hashCode()
	{
		StringBuilder S1 = new StringBuilder();
		S1.append(this.loop_name_);
		S1.append(this.label_);
		Node<Object> CurrNode = this.dependencies_.getFirst();
		while (CurrNode != null)
		{
			S1.append(CurrNode.entry.toString());
			CurrNode = CurrNode.next;
		}
		return S1.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object C1)
	{
		if (C1 == null)
			return false;
					
		if (this.hashCode() == C1.hashCode())
			return true;
		else
			return false;
		
	}
	
	public void deisplay()
	{
		System.out.println(this.label_);
		System.out.println(this.loop_name_);
		System.out.println(this.dependencies_.toString());
	}
}
