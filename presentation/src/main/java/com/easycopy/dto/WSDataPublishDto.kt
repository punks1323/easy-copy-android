package com.easycopy.dto

import com.fasterxml.jackson.annotation.JsonRawValue

/**
 * @author pankaj
 * @version 1.0
 * @since 2021-05-11
 */
class WSDataPublishDto {

    var dataType: String? = null

    @JsonRawValue
    var data: String? = null

}