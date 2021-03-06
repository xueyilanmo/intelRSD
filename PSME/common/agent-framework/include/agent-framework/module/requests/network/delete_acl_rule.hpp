/*!
 * @copyright
 * Copyright (c) 2016-2017 Intel Corporation
 *
 * @copyright
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * @copyright
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * @copyright
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * @file requests/network/delete_acl_rule.hpp
 * @brief network DeleteAclRule request
 * */

#pragma once

#include "agent-framework/module/model/attributes/oem.hpp"
#include "agent-framework/module/enum/network.hpp"
#include "agent-framework/module/constants/command.hpp"
#include "agent-framework/module/constants/network.hpp"
#include "agent-framework/module/utils/optional_field.hpp"
#include "agent-framework/validators/procedure_validator.hpp"

#include <string>

namespace Json {
    class Value;
}

namespace agent_framework {
namespace model {
namespace requests {

/*! network DeleteAclRule request */
class DeleteAclRule{
public:
    /*!
     * @brief explicit DeleteAclRule request constructor
     *
     * @param[in] rule Rule identifier
     * @param[in] oem OEM specific data
     * */
    explicit DeleteAclRule(const std::string& rule,
                           agent_framework::model::attribute::Oem oem = agent_framework::model::attribute::Oem{});

    /*!
     * @brief Get command name
     *
     * @return Command name
     * */
    static std::string get_command() {
        return literals::Command::DELETE_ACL_RULE;
    }

    /*!
     * @brief Get ACL rule UUID
     *
     * @return UUID as std::string
     * */
    const std::string& get_rule() const {
        return m_rule;
    }

    /*!
     * @brief Get OEM data
     *
     * @return OEM data
     * */
    const agent_framework::model::attribute::Oem& get_oem() const {
        return m_oem;
    }

    /*!
     * @brief Transform request to Json
     *
     * @return created Json value
     * */
    Json::Value to_json() const;

    /*!
     * @brief create DeleteAclRule from Json
     *
     * @param[in] json the input argument
     *
     * @return new DeleteAclRule
     * */
    static DeleteAclRule from_json(const Json::Value& json);

    /*!
     * @brief Return procedure schema
     *
     * @return procedure schema
     * */
    static const jsonrpc::ProcedureValidator& get_procedure() {
        static const jsonrpc::ProcedureValidator procedure{
            get_command(), jsonrpc::PARAMS_BY_NAME, jsonrpc::JSON_OBJECT,
            literals::AclRule::RULE, VALID_UUID,
            literals::Acl::OEM, jsonrpc::JSON_OBJECT,
            nullptr
        };
        return procedure;
    }

private:
    std::string m_rule{};
    agent_framework::model::attribute::Oem m_oem{};
};

}
}
}

