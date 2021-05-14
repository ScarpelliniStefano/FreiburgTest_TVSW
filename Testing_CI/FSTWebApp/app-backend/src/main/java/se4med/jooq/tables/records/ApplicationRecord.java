/*
 * This file is generated by jOOQ.
 */
package se4med.jooq.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import se4med.jooq.tables.Application;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ApplicationRecord extends UpdatableRecordImpl<ApplicationRecord> implements Record5<String, Integer, String, String, Byte> {

    private static final long serialVersionUID = -84869276;

    /**
     * Setter for <code>se4med.application.id</code>. questa tabella memorizza tutte le applicazioni. per ogni applicazione memorizza a quale progetto appartiene, il nome, una descrizione e se è una app web o no.

id applicazione, identifica univocamente l'applicazione
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>se4med.application.id</code>. questa tabella memorizza tutte le applicazioni. per ogni applicazione memorizza a quale progetto appartiene, il nome, una descrizione e se è una app web o no.

id applicazione, identifica univocamente l'applicazione
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>se4med.application.idproject</code>. fa riferimento all'id del progetto al quale l'applicazione appartiene -&gt; FK

se l'id della tabella project viene aggiornato, viene aggiornato anche idproject
se l'id della tabella project viene cancellato e c'è un record che contiene quell'id, non viene permessa la cancellazione del progetto
     */
    public void setIdproject(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>se4med.application.idproject</code>. fa riferimento all'id del progetto al quale l'applicazione appartiene -&gt; FK

se l'id della tabella project viene aggiornato, viene aggiornato anche idproject
se l'id della tabella project viene cancellato e c'è un record che contiene quell'id, non viene permessa la cancellazione del progetto
     */
    public Integer getIdproject() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>se4med.application.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>se4med.application.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>se4med.application.description</code>.
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>se4med.application.description</code>.
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>se4med.application.webApp</code>. indica se l'applicazione è un'applicazione web oppure è un altro tipo di applicazione (es: per smartphone)
0 -&gt; NO WEB APP
1 -&gt; WEB APP
     */
    public void setWebapp(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>se4med.application.webApp</code>. indica se l'applicazione è un'applicazione web oppure è un altro tipo di applicazione (es: per smartphone)
0 -&gt; NO WEB APP
1 -&gt; WEB APP
     */
    public Byte getWebapp() {
        return (Byte) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, Integer, String, String, Byte> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<String, Integer, String, String, Byte> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Application.APPLICATION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Application.APPLICATION.IDPROJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Application.APPLICATION.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Application.APPLICATION.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return Application.APPLICATION.WEBAPP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getIdproject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component5() {
        return getWebapp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getIdproject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getWebapp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRecord value2(Integer value) {
        setIdproject(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRecord value5(Byte value) {
        setWebapp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationRecord values(String value1, Integer value2, String value3, String value4, Byte value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ApplicationRecord
     */
    public ApplicationRecord() {
        super(Application.APPLICATION);
    }

    /**
     * Create a detached, initialised ApplicationRecord
     */
    public ApplicationRecord(String id, Integer idproject, String name, String description, Byte webapp) {
        super(Application.APPLICATION);

        set(0, id);
        set(1, idproject);
        set(2, name);
        set(3, description);
        set(4, webapp);
    }
}
