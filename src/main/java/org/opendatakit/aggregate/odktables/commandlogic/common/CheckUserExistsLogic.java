package org.opendatakit.aggregate.odktables.commandlogic.common;

import org.opendatakit.aggregate.odktables.command.common.CheckUserExists;
import org.opendatakit.aggregate.odktables.commandlogic.CommandLogic;
import org.opendatakit.aggregate.odktables.commandresult.common.CheckUserExistsResult;
import org.opendatakit.aggregate.odktables.relation.Users;
import org.opendatakit.common.persistence.exception.ODKDatastoreException;
import org.opendatakit.common.web.CallingContext;

/**
 * CheckUserExistsLogic encapsulates the logic necessary to validate and execute
 * a CheckUserExists command.
 * 
 * @author the.dylan.price@gmail.com
 * 
 */
public class CheckUserExistsLogic extends CommandLogic<CheckUserExists>
{

    private final CheckUserExists checkUserExists;

    public CheckUserExistsLogic(CheckUserExists checkUserExists)
    {
        this.checkUserExists = checkUserExists;
    }

    @Override
    public CheckUserExistsResult execute(CallingContext cc)
            throws ODKDatastoreException
    {
        Users users = Users.getInstance(cc);
        
        // TODO: this is kind of hacky, but we need to ensure the admin user exists
        users.getAdminUser();
        
        String userID = checkUserExists.getUserID();
        boolean userExists = users.query().equal(Users.USER_ID, userID)
                .exists();

        return CheckUserExistsResult.success(userExists);
    }
}