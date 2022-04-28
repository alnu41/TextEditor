package finalproject;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import javax.swing.undo.*;

public class UTextArea extends TextArea implements StateEditable{

	private final static String KEY_STATE="UndoableTextAreaKey";
	private boolean textChanged = false;
	private StateEdit currentEdit;
	private UndoManager undoManager;
	
	@Override
	public void storeState(Hashtable state) {
		// TODO Auto-generated method stub
		state.put(KEY_STATE,getText());
	}

	@Override
	public void restoreState(Hashtable state) {
		// TODO Auto-generated method stub
		Object data=state.get(KEY_STATE);
		if(data!=null) {
			setText((String)data);
		
		}
	}
	
	public UTextArea() {
		super();
		initUndoable();
	}
	
	public UTextArea(String string) {
		super();
		initUndoable();
	}
	
	public UTextArea(int row, int column) {
		super(row,column);
		initUndoable();
	}
	
	public UTextArea(String string,int row, int column) {
		super(string,row,column);
		initUndoable();
	}
	
	public UTextArea(String string,int row, int column, int scrollbars) {
		super(string,row,column,scrollbars);
		initUndoable();
	}

	private void initUndoable() {
		// TODO Auto-generated method stub
		undoManager =new UndoManager();
		currentEdit=new StateEdit(this);
		addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent event)
					{
						if(event.isActionKey())
						{
							takeSnapshot();
						}
					}
				});
		
		addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent fe)
			{
				takeSnapshot();
			}
		});
		
		addTextListener(new TextListener() {
			
			@Override
			public void textValueChanged(TextEvent e) {
				// TODO Auto-generated method stub
				textChanged=true;
				takeSnapshot();
			}
		});
	}
	
	private void takeSnapshot() {
		// TODO Auto-generated method stub
		if(textChanged)
		{
			currentEdit.end();
			undoManager.addEdit(currentEdit);
			textChanged=false;
			currentEdit=new StateEdit(this);
		}
	}

	public boolean undo() {
		try {
			undoManager.undo();
			return true;
		}catch(CannotUndoException e)
		{
			System.out.println("cannot undo");
			return false;
		}
	}
	
	public boolean redo() {
		try {
			undoManager.redo();
			return true;
		}catch(CannotRedoException e)
		{
			System.out.println("cannot redo");
			return false;
		}
	}
	
}
