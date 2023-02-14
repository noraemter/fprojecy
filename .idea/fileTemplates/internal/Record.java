/**
 * @author: Amir Eleyan
 * ID: 1191076
 * @created: ${DATE}    ${TIME}
 */
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
public record ${NAME}() {
}
