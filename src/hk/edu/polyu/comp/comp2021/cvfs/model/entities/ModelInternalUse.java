package hk.edu.polyu.comp.comp2021.cvfs.model.entities;

/**
 * <h3>The {@code @ModelInternalUse} Annotation</h3>
 *
 * This annotation indicates that a method should [only] be used within the Model part (i.e., the Entities - {@code entities} package and the File System - {@code FileSystem} class), and should [not] be exposed to the other parts.
 *
 * <p>
 *
 * The main reasons for using this {@code @ModelInternalUse} annotation include:
 * <ul>
 *     <li>Some complex mechanisms, such as the actual disk storage logic, are not simulated. It is best not to expose such unsimulated things to other parts, and related methods should add this annotation and only be used internally. In addition, even with complete simulation, such methods should keep this annotation, refer to the second and third points.</li>
 *     <li>Many methods are safely used within the Model part, but exposing it to other parts could lead to risks, such as unpredictable data changes. These methods should add this annotation.</li>
 *     <li>Many methods are intended to be controlled and managed by the file system, and they are designed not to be exposed to other parts. These methods should add this annotation.</li>
 * </ul>
 *
 * <p>
 *
 * <b>Please check the contexts and perform necessary error checking before using methods with this annotation.</b>
 */
public @interface ModelInternalUse {
}

