package render;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Shader {

	private int program, vertexShader, fragmentShader;

	public Shader(String filename) {
		program = glCreateProgram();

		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readFile(filename + ".vs"));
		glCompileShader(vertexShader);
		checkShader(vertexShader);

		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, readFile(filename + ".fs"));
		glCompileShader(fragmentShader);
		checkShader(fragmentShader);

		attachShaders();

		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");

		glLinkProgram(program);
		checkProgramFor(GL_LINK_STATUS);
		glValidateProgram(program);
		checkProgramFor(GL_VALIDATE_STATUS);
	}

	protected void finalize() throws Throwable {
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(program);
		super.finalize();
	}

	public void bind() {
		glUseProgram(program);
	}

	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		if (location != -1)
			glUniform1i(location, value);

	}

	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		if (location != -1) {
			FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
			value.get(buffer);
			glUniformMatrix4fv(location, false, buffer);
		}
	}

	private void checkShader(int id) {
		if (glGetShaderi(id, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(id));
			System.exit(1);
		}
	}

	private void checkProgramFor(int Status) {
		if (glGetProgrami(program, Status) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}

	}

	private void attachShaders() {
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
	}

	private String readFile(String filename) {
		StringBuilder string = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File("./etc/shaders/" + filename)));
			String line;
			while ((line = reader.readLine()) != null) {
				string.append(line);
				string.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}
}
