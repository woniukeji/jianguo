package com.haibin.qiaqia.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by invinjun on 2016/6/20.
 */
public class ListChaoCommodity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("sales_sum")
    @Expose
    private Integer salesSum;
    @SerializedName("chao_class_id")
    @Expose
    private Integer chaoClassId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("shelf_life")
    @Expose
    private String shelfLife;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("p_date")
    @Expose
    private String pDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sum")
    @Expose
    private Integer sum;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The salesSum
     */
    public Integer getSalesSum() {
        return salesSum;
    }

    /**
     *
     * @param salesSum
     * The sales_sum
     */
    public void setSalesSum(Integer salesSum) {
        this.salesSum = salesSum;
    }

    /**
     *
     * @return
     * The chaoClassId
     */
    public Integer getChaoClassId() {
        return chaoClassId;
    }

    /**
     *
     * @param chaoClassId
     * The chao_class_id
     */
    public void setChaoClassId(Integer chaoClassId) {
        this.chaoClassId = chaoClassId;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The price
     */
    public Double getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The shelfLife
     */
    public String getShelfLife() {
        return shelfLife;
    }

    /**
     *
     * @param shelfLife
     * The shelf_life
     */
    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    /**
     *
     * @return
     * The alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     *
     * @param alias
     * The alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     *
     * @return
     * The pDate
     */
    public String getPDate() {
        return pDate;
    }

    /**
     *
     * @param pDate
     * The p_date
     */
    public void setPDate(String pDate) {
        this.pDate = pDate;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The sum
     */
    public Integer getSum() {
        return sum;
    }

    /**
     *
     * @param sum
     * The sum
     */
    public void setSum(Integer sum) {
        this.sum = sum;
    }

}

