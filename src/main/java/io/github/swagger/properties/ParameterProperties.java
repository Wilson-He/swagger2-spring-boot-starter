package io.github.swagger.properties;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.ToString;
import org.springframework.core.Ordered;
import springfox.documentation.schema.Example;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.VendorExtension;

import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.BuilderDefaults.defaultIfAbsent;
import static springfox.documentation.builders.BuilderDefaults.emptyToNull;

/**
 * Swagger {@link Parameter} properties converter
 *
 * @author Wilson
 */
@ToString
public class ParameterProperties {
    /**
     * 参数名
     */
    private String name;
    private String description;
    private String defaultValue;
    private boolean required;
    private boolean allowMultiple;
    private AllowableValues allowableValues;
    /**
     * can be header, cookie, body, query.body just retain, not helpful.
     */
    private String paramType = "header";
    private String paramAccess;
    private String modelRef = "string";
    private boolean hidden;
    private String pattern;
    private List<VendorExtension> vendorExtensions = newArrayList();
    private String collectionFormat = null;
    private Boolean allowEmptyValue;
    private int order = Ordered.LOWEST_PRECEDENCE;
    private Object scalarExample;
    private Multimap<String, Example> examples = LinkedListMultimap.create();

    /**
     * Updates the globalParameter name
     *
     * @param name - name of the globalParameter
     * @return this
     */
    public ParameterProperties setName(String name) {
        this.name = defaultIfAbsent(name, this.name);
        return this;
    }

    /**
     * Updates the description of the globalParameter
     *
     * @param description - description
     * @return this
     */
    public ParameterProperties setDescription(String description) {
        this.description = defaultIfAbsent(description, this.description);
        return this;
    }

    /**
     * Updates the default value of the globalParameter
     *
     * @param defaultValue - default value
     * @return this
     */
    public ParameterProperties setDefaultValue(String defaultValue) {
        this.defaultValue = defaultIfAbsent(defaultValue, this.defaultValue);
        return this;
    }

    /**
     * Updates if the globalParameter is required or optional
     *
     * @param required - flag to indicate if the globalParameter is required
     * @return this
     */
    public ParameterProperties setRequired(boolean required) {
        this.required = required;
        return this;
    }

    /**
     * Updates if the globalParameter should allow multiple values
     *
     * @param allowMultiple - flag to indicate if the globalParameter supports multi-value
     * @return this
     */
    public ParameterProperties setAllowMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
        return this;
    }

    /**
     * Updates if the globalParameter is bound by a range of values or a range of numerical values
     *
     * @param allowableValues - allowable values (instance of @see springfox.documentation.service.AllowableListValues
     *                        or @see springfox.documentation.service.AllowableRangeValues)
     * @return this
     */
    public ParameterProperties setAllowableValues(AllowableValues allowableValues) {
        this.allowableValues = emptyToNull(allowableValues, this.allowableValues);
        return this;
    }

    /**
     * Updates the globalParameter access
     *
     * @param paramAccess - globalParameter access
     * @return this
     */
    public ParameterProperties setParameterAccess(String paramAccess) {
        this.paramAccess = defaultIfAbsent(paramAccess, this.paramAccess);
        return this;
    }

    /**
     * Represents the convenience method to infer the model reference
     * Consolidate or figure out whats can be rolled into the other.
     *
     * @param modelRef model reference
     * @return this
     */
    public ParameterProperties setModelRef(String modelRef) {
        this.modelRef = defaultIfAbsent(modelRef, this.modelRef);
        return this;
    }

    /**
     * Updates if the globalParameter is hidden
     *
     * @param hidden - flag to indicate if the globalParameter is hidden
     * @return this
     */
    public ParameterProperties setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    /**
     * Updates the globalParameter extensions
     *
     * @param collectionFormat - globalParameter collection format
     * @return this
     * @since 2.8.0
     */
    public ParameterProperties setCollectionFormat(String collectionFormat) {
        this.collectionFormat = defaultIfAbsent(collectionFormat, this.collectionFormat);
        return this;
    }

    /**
     * Updates the flag that allows sending empty values for this globalParameter
     *
     * @param allowEmptyValue - true/false
     * @return this
     * @since 2.8.1
     */
    public ParameterProperties setAllowEmptyValue(Boolean allowEmptyValue) {
        this.allowEmptyValue = defaultIfAbsent(allowEmptyValue, this.allowEmptyValue);
        return this;
    }

    /**
     * Updates default order of precedence of globalParameters
     *
     * @param order - between {@link Ordered#HIGHEST_PRECEDENCE}, {@link Ordered#LOWEST_PRECEDENCE}
     * @return this
     * @since 2.8.1
     */
    public ParameterProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    public ParameterProperties setPattern(String pattern) {
        this.pattern = defaultIfAbsent(pattern, this.pattern);
        return this;
    }

    /**
     * @param scalarExample example for non-body globalParameters
     * @return this
     * @since 2.8.1
     */
    public ParameterProperties setScalarExample(Object scalarExample) {
        this.scalarExample = defaultIfAbsent(scalarExample, this.scalarExample);
        return this;
    }

    /**
     * @param examples example for body globalParameters
     * @return this
     * @since 2.8.1
     */
    public ParameterProperties setComplexExamples(Multimap<String, Example> examples) {
        this.examples.putAll(examples);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isAllowMultiple() {
        return allowMultiple;
    }

    public AllowableValues getAllowableValues() {
        return allowableValues;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamAccess() {
        return paramAccess;
    }

    public void setParamAccess(String paramAccess) {
        this.paramAccess = paramAccess;
    }

    public String getModelRef() {
        return modelRef;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getPattern() {
        return pattern;
    }

    public String getCollectionFormat() {
        return collectionFormat;
    }

    public Boolean getAllowEmptyValue() {
        return allowEmptyValue;
    }

    public int getOrder() {
        return order;
    }

    public Object getScalarExample() {
        return scalarExample;
    }

    public Multimap<String, Example> getExamples() {
        return examples;
    }

    public void setExamples(Multimap<String, Example> examples) {
        this.examples = examples;
    }

    public Parameter build() {
        if (!ImmutableList.of("query", "formData").contains(paramType)) {
            allowEmptyValue = null;
        }
        Objects.requireNonNull(name, "globalParameter cannot be null");
        Objects.requireNonNull(paramType, "globalParameter type cannot be null(query,header,cookie,body)");
        return new Parameter(
                name,
                description,
                defaultValue,
                required,
                allowMultiple,
                allowEmptyValue,
                new ModelRef(modelRef),
                Optional.absent(),
                allowableValues,
                paramType,
                paramAccess,
                hidden,
                pattern,
                collectionFormat,
                order,
                scalarExample,
                examples,
                vendorExtensions);
    }
}
