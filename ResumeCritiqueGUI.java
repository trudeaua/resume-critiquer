
/*
 * This program takes a resume file 
 * (as an ASCII .txt document) and 
 * evaluates the quality of it.
 * i.e. It tells the user whether 
 * they should include sections
 * that are vital to a good resume
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

public class ResumeCritiqueGUI {

	public static File fileChoose() {
		JFileChooser input = new JFileChooser();
		int returnVal = input.showOpenDialog(null);
		File fileName = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fileName = input.getSelectedFile();
		}
		return fileName;
	}
	public static void scrollBar(JTextArea area, JFrame window){
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		window.add(scroll);
	}
	public static JTextArea createArea() {
		final JTextArea createdArea = new JTextArea();
		createdArea.setEditable(false);
		createdArea.setVisible(true);
		return createdArea;
	}

	public static ArrayList<String> processResume(File fileName) throws IOException {
		// imports the file and processes it into an array
		// each element in the array is a line in the resume
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String str = null;
		ArrayList<String> lines = new ArrayList<String>();
		while ((str = reader.readLine()) != null) {
			lines.add(str);
		}
		reader.close();
		return lines;
	}
	public static String[] splitLines(String[] arrName) {
		//creates an array made up of each word in the resume
		String[] splitLine = new String[arrName.length];
		for (int j = 0; j < arrName.length; j++) {
			splitLine[j] = Arrays.toString(arrName[j].split(" "));
		}
		return splitLine;
	}
	public static String[] arrLines(ArrayList<String> listName, JTextArea area) {
		//prints out the resume on screen
		String[] linesArray = listName.toArray(new String[listName.size()]);
		for (int i = 0; i < linesArray.length; i++) {
			area.append(" " + linesArray[i] + "\n");
		}
		return linesArray;
	}
	public static String[] correctSort(String[] resume) {
		//looks for the keywords, puts the found keywords in an array
		String[] keywords = { "Project", "Education", "Qualification", "Experience", "Skill", "@", "linkedin", "Extra", "Language", "Interest", "Reference" };
		String[] correctArr = new String[keywords.length];
		for (int m = 0; m < keywords.length; m++) {
			for (int k = 0; k < resume.length; k++) {
				if (resume[k].indexOf(keywords[m]) >= 0) {
					correctArr[m] = keywords[m];
				}
			}
		}
		return correctArr;
	}
	public static String[] missingSort(String[] resume) {
		//puts not found keywords in an array
		String[] keywords = { "Project", "Education", "Qualification", "Experience", "Skill", "@", "linkedin", "Extra", "Language", "Interest", "Reference" };
		String[] missingArr = new String[keywords.length];
		String[] correctWords = correctSort(resume);
		for (int i = 0; i < keywords.length; i++) {
			if (correctWords[i] == null) {
				missingArr[i] = keywords[i];
			}
		}
		return missingArr;
	}
	public static ArrayList<String> finalCorrectArr(String[] inputArray) {
		//prints the found keywords on screen
		ArrayList<String> finalCorrect = new ArrayList<String>();
		for (int i = 0; i < inputArray.length; i++) {
			if (inputArray[i] != null) {
				if (inputArray[i].equals("@")) {
					finalCorrect.add("Email");
				} else if (inputArray[i].equals("linkedin")) {
					finalCorrect.add("LinkedIn");
				} else if (inputArray[i].equals("Project")) {
					finalCorrect.add("Project Work");
				} else if (inputArray[i].equals("Education")) {
					finalCorrect.add("Education");
				} else if (inputArray[i].equals("Extra")) {
					finalCorrect.add("Extra-Curriculars");
				} else if (inputArray[i].equals("Activities")) {
					finalCorrect.add("Activities");
				} else {
					finalCorrect.add(inputArray[i] + "s");
				}
			}
		}
		return finalCorrect;
	}
	public static ArrayList<String> finalMissingArr(String[] inputArray) {
		//prints not found keywords on screen
		ArrayList<String> finalMissing = new ArrayList<String>();
		for (int i = 0; i < inputArray.length; i++) {
			if (inputArray[i] != null) {
				if (inputArray[i].equals("@")) {
					finalMissing.add("Email");
				} else if (inputArray[i].equals("linkedin")) {
					finalMissing.add("LinkedIn URL");
				} else if (inputArray[i].equals("Project")) {
					finalMissing.add("Project Work");
				} else if (inputArray[i].equals("Extra")) {
					finalMissing.add("Extra-Curriculars");
				} else {
					finalMissing.add(inputArray[i] + "s");
				}
			}
		}
		return finalMissing;
	}
	public static void main(String[] args) throws IOException {

		// initialize GUI window
		JFrame window = new JFrame("Resume Critique");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(720, 540);
		window.getContentPane();
		// opens file selection window
		JOptionPane.showMessageDialog(null,
				"Only ASCII .txt files are accepted in this program. Convert your resume to a standard .txt document before running this program.");
		File file = fileChoose();
		JTextArea area = createArea();
		// add lines of the file to an array
		ArrayList<String> lines = processResume(file);

		area.setText(" YOUR RESUME: " + "\n");
		window.getContentPane().add(BorderLayout.CENTER, area);
		window.setVisible(true);

		// create a scroll bar
		JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		window.add(scroll);
		// output lines of file to the GUI textArea
		String[] linesArray = arrLines(lines, area);
		String[] split = splitLines(linesArray);
		String[] correct = correctSort(split);
		String[] missing = missingSort(split);

		ArrayList<String> finalCorrect = finalCorrectArr(correct);
		ArrayList<String> finalMissing = finalMissingArr(missing);
		area.append(
				"***************************************************************************************************\n");
		// feedback on resume
		if (finalCorrect.size() > 0) {
			area.append("\n" + " RESULTS: " + "\n"
					+ " Good Job! We were able to find these sections/items in your resume:" + "\n\n");
			for (int i = 0; i < finalCorrect.size(); i++) {
				area.append(" - " + finalCorrect.get(i) + "\n");
			}
			area.append(
					"\n Even though you have the above sections, make sure to include enough information so\n the employer really knows how valuable of an employee you are! Don't include too much\n information, however; clear and concise bullet points should do the trick.\n\n");
			if (finalMissing.size() == 0) {
				area.append(
						"\n Congratulations! We didn't find anything wrong with your resume! Now get out there\n and land that dream job!");
			} else if (finalMissing.size() > 0) {
				area.append("\n" + " We weren't able to find these sections(*), consider adding them to improve" + "\n"
						+ " the quality and effectiveness of your resume:" + "\n\n");
				for (int i = 0; i < finalMissing.size(); i++) {
					area.append(" - " + finalMissing.get(i) + "\n");
					if (finalMissing.get(i).equals("Project Work")) {
						area.append(
								" It's important to include project work in a resume. It shows employers that" + "\n"
										+ " you are passionate about your field of interest outside the classroom. Projects also allow employers to"
										+ "\n"
										+ " gauge how you work in teams, and how much 'extra' experience you have with your area of study"
										+ "\n\n");
					} else if (finalMissing.get(i).equals("Experiences")) {
						area.append(
								" Experience shows what prior training you have coming into the job. It shows" + "\n"
										+ " employers that you have actively involved yourself in roles that relate the the job you are applying "
										+ "\n"
										+ " to. Employers will often hire employees with more experience over those with less."
										+ "\n\n");
					} else if (finalMissing.get(i).equals("Education")) {
						area.append(" Education is one of the important factors that most employers take into account."
								+ "\n"
								+ " Your degree/diploma gives employers a feel about how knowledgable you are about the job. More "
								+ "\n"
								+ " often than not, a certain level of education is required to apply for specific"
								+ "\n" + " jobs.\n\n");
					} else if (finalMissing.get(i).equals("Skills")) {
						area.append(
								" Skills come in two types: Hard and Soft. Hard skills are specific to the job" + "\n"
										+ " (i.e. if you are applying to be a mechanic, you should have certain automotive skills.). Soft "
										+ "\n"
										+ " skills apply to every job, and include things like communication, responsibility,"
										+ "\n" + " etc.\n\n");
					} else if (finalMissing.get(i).equals("Email")) {
						area.append(
								" You should always include some sort of contact information in a resume. With" + "\n"
										+ " humanity's widespread use of technology, nearly everybody in the working world has an email "
										+ "\n"
										+ " account. Email is a fast and easy form of communication between employer and "
										+ "\n" + " employee.\n\n");
					} else if (finalMissing.get(i).equals("LinkedIn URL")) {
						area.append(" LinkedIn is a widely used social network for those looking to expand their" + "\n"
								+ " business network, and it also allows employers to view your profile, and view skills, past jobs and"
								+ "\n" + " experience, volunteer work, and more. A LinkedIn account will help develop"
								+ "\n" + " your primary relationship with your employer.\n\n");
					} else if (finalMissing.get(i).equals("Extra-Curriculars")) {
						area.append(" Extra-Curriculars/Activities help you develop both your social skills, and" + "\n"
								+ " your knowledge of your field of study (and even other fields!). They show how involved you are,"
								+ "\n" + " and assist you in seeming easier to work with, and more appealing as an"
								+ "\n" + " employee.\n\n");
					} else if (finalMissing.get(i).equals("Qualifications")) {
						area.append(" Certain qualifications will help you seem more appealing to most employers. "
								+ "\n"
								+ " For example, workplace safety is an important factor in every job, so employers are automatically"
								+ "\n" + " more inclined to hire employees with qualifications such as First Aid,"
								+ "\n" + " WHMIS, etc..\n\n");
					} else if (finalMissing.get(i).equals("Languages")) {
						area.append(" Speaking more than one language is a very useful skill. It can improve company-"
								+ "\n"
								+ " customer relations, and in some job fields (such as government), speaking more than one"
								+ "\n" + " language may even be required to be considered for a job role." + "\n\n");
					} else if (finalMissing.get(i).equals("Interests")) {
						area.append(" Although not greatly influential on a resume, personal interests may be one of "
								+ "\n" + " the deciding factors on hiring you as an employee. If the employer can "
								+ "\n"
								+ " relate to your interests, then they will form a personal connection and be more"
								+ "\n" + " inclined to hire you." + "\n\n");
					} else if (finalMissing.get(i).equals("References")) {
						area.append(" References allow an employer to physically gauge how good of an employer you are"
								+ "\n"
								+ " through your past employers. They may/may not want to know more about your work experience,"
								+ "\n" + " so including previous employer information (with their permission) is "
								+ "\n" + " important.\n\n");
					}
				}
			} else if (finalMissing.size() > 0) {
				area.append("\n" + " We weren't able to find these sections(*), consider adding them to improve" + "\n"
						+ " the quality and effectiveness of your resume:" + "\n\n");
				for (int i = 0; i < finalMissing.size(); i++) {
					area.append(" - " + finalMissing.get(i) + "\n");
					if (finalMissing.get(i).equals("Project Work")) {
						area.append(
								" It's important to include project work in a resume. It shows employers that" + "\n"
										+ " you are passionate about your field of interest outside the classroom. Projects also allow employers to"
										+ "\n"
										+ " gauge how you work in teams, and how much 'extra' experience you have with your area of study"
										+ "\n\n");
					} else if (finalMissing.get(i).equals("Experiences")) {
						area.append(
								" Experience shows what prior training you have coming into the job. It shows" + "\n"
										+ " employers that you have actively involved yourself in roles that relate the the job you are applying "
										+ "\n"
										+ " to. Employers will often hire employees with more experience over those with less."
										+ "\n\n");
					} else if (finalMissing.get(i).equals("Education")) {
						area.append(" Education is one of the important factors that most employers take into account."
								+ "\n"
								+ " Your degree/diploma gives employers a feel about how knowledgable you are about the job. More "
								+ "\n"
								+ " often than not, a certain level of education is required to apply for specific"
								+ "\n" + " jobs.\n\n");
					} else if (finalMissing.get(i).equals("Skills")) {
						area.append(
								" Skills come in two types: Hard and Soft. Hard skills are specific to the job" + "\n"
										+ " (i.e. if you are applying to be a mechanic, you should have certain automotive skills.). Soft "
										+ "\n"
										+ " skills apply to every job, and include things like communication, responsibility,"
										+ "\n" + " etc.\n\n");
					} else if (finalMissing.get(i).equals("Email")) {
						area.append(
								" You should always include some sort of contact information in a resume. With" + "\n"
										+ " humanity's widespread use of technology, nearly everybody in the working world has an email "
										+ "\n"
										+ " account. Email is a fast and easy form of communication between employer and "
										+ "\n" + " employee.\n\n");
					} else if (finalMissing.get(i).equals("LinkedIn URL")) {
						area.append(" LinkedIn is a widely used social network for those looking to expand their" + "\n"
								+ " business network, and it also allows employers to view your profile, and view skills, past jobs and"
								+ "\n" + " experience, volunteer work, and more. A LinkedIn account will help develop"
								+ "\n" + " your primary relationship with your employer.\n\n");
					} else if (finalMissing.get(i).equals("Extra-Curriculars")) {
						area.append(" Extra-Curriculars/Activities help you develop both your social skills, and" + "\n"
								+ " your knowledge of your field of study (and even other fields!). They show how involved you are,"
								+ "\n" + " and assist you in seeming easier to work with, and more appealing as an"
								+ "\n" + " employee.\n\n");
					} else if (finalMissing.get(i).equals("Qualifications")) {
						area.append(" Certain qualifications will help you seem more appealing to most employers. "
								+ "\n"
								+ " For example, workplace safety is an important factor in every job, so employers are automatically"
								+ "\n" + " more inclined to hire employees with qualifications such as First Aid,"
								+ "\n" + " WHMIS, etc..\n\n");
					} else if (finalMissing.get(i).equals("Languages")) {
						area.append(" Speaking more than one language is a very useful skill. It can improve company-"
								+ "\n"
								+ " customer relations, and in some job fields (such as government), speaking more than one"
								+ "\n" + " language may even be required to be considered for a job role." + "\n\n");
					} else if (finalMissing.get(i).equals("Interests")) {
						area.append(" Although not greatly influential on a resume, personal interests may be one of "
								+ "\n" + " the deciding factors on hiring you as an employee. If the employer can "
								+ "\n"
								+ " relate to your interests, then they will form a personal connection and be more"
								+ "\n" + " inclined to hire you." + "\n\n");
					} else if (finalMissing.get(i).equals("References")) {
						area.append(" References allow an employer to physically gauge how good of an employer you are"
								+ "\n"
								+ " through your past employers. They may/may not want to know more about your work experience,"
								+ "\n" + " so including previous employer information (with their permission) is "
								+ "\n" + " important.\n\n");
					}
				}
			}
			if (linesArray.length > 76) {
				area.append("\n" + " Also, your resume exceeds 2 pages in length. Employers don't like to read long"
						+ "\n" + " resumes, so try reducing/eliminating some sections." + "\n");
			}
			area.append(
					"\n *If you think that you included a section, and we listed it as not being there, check your\n spelling and grammar. Correct spelling, grammar, as well as formatting are very\n important factors in any good resume.");

		} else {
			area.append("\n Empty resume! You've got some work to do.");
		}
	}
}