package ca.jrvs.apps.practice;

public interface RegexExc {
    /**
     * return true if file extension ends in jpeg or jpg
     * @param filename
     * @return
     */
    public boolean matchJpeg(String filename);

    /**
     * return true if ip is valid (0.0.0.0 to 999.999.999.999)
     * @param i
     * @return
     */
    public boolean matchIP(String i);

    /**
     * returns true if line is empty or has white space characters
     * @param line
     * @return
     */
    public boolean isEmptyLine(String line);

}
