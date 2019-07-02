package Attempt1_Determinant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class mixtures {
	public static void main(String[] args) throws IOException {
		List<String> list = getInput(); //Formats each line as string entity
		double[][] a = new double[list.size()-1][list.size()];
		for (int i=0; i<list.size(); i++) { //Set up the array and establish values
			String str = list.get(i);
			List<String> tempList = Arrays.asList(str.split(":"));
			for(int j=0; j<tempList.size(); j++) {
				if(i==0) {
					a[j][list.size()-1] = Double.parseDouble(tempList.get(j));
				} else {
					a[j][i-1] = Double.parseDouble((tempList.get(j)));
				}
			}
		}
		double[][] mat = rref(a);
		String[] f_mat = new String[mat.length];
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat[0].length; j++) {
				System.out.print(mat[i][j]+" ");
				f_mat[i] = Integer.toString((int) Math.round((float) mat[i][mat[i].length-1]));
			}
			System.out.println();
		}
		for(int i=0; i<f_mat.length; i++) {
			System.out.println(f_mat[i]);
			
		}
		
		list = Arrays.asList(f_mat);	//Output to file
		Charset utf8 = StandardCharsets.UTF_8;
		try {
			Files.write(Paths.get("answer.txt"), list, utf8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static double[][] rref(double[][] mat) {
		double[][] rref = new double[mat.length][];
		for (int i = 0; i < mat.length; i++)
			rref[i] = Arrays.copyOf(mat[i], mat[i].length);

		int r = 0;
		for (int c = 0; c < rref[0].length && r < rref.length; c++) {
			int j = r;
			for (int i = r + 1; i < rref.length; i++)
				if (Math.abs(rref[i][c]) > Math.abs(rref[j][c]))
					j = i;
			if (Math.abs(rref[j][c]) < 0.00001)
				continue;

			double[] temp = rref[j];
			rref[j] = rref[r];
			rref[r] = temp;

			double s = 1.0 / rref[r][c];
			for (j = 0; j < rref[0].length; j++)
				rref[r][j] *= s;
			for (int i = 0; i < rref.length; i++) {
				if (i != r) {
					double t = rref[i][c];
					for (j = 0; j < rref[0].length; j++)
						rref[i][j] -= t * rref[r][j];
				}
			}
			r++;
		}

		return rref;
	}
	
	public static List<String> getInput() throws IOException {
		List<String> list = new ArrayList<String>();
		ZipFile zipFile = new ZipFile("C:\\Users\\Randall_Thornton\\Downloads\\input.zip");
		ZipEntry zipEntry = zipFile.getEntry("solutions.txt");
		InputStream iStream = zipFile.getInputStream(zipEntry);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(iStream));
			String text = null;

			while ((text = br.readLine()) != null) {
				list.add(text);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return list;		
	}
}