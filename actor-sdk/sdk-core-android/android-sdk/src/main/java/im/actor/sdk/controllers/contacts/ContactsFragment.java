package im.actor.sdk.controllers.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.actor.core.entity.Contact;
import im.actor.core.viewmodel.CommandCallback;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.controllers.Intents;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class ContactsFragment extends BaseContactFragment {

    public ContactsFragment() {
        super(false, false, false);
        setRootFragment(true);
        setHomeAsUp(true);
        setTitle(R.string.contacts_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View res = onCreateContactsView(R.layout.fragment_contacts, inflater, container, savedInstanceState);
        res.setBackgroundColor(ActorSDK.sharedActor().style.getMainBackgroundColor());
        res.findViewById(R.id.emptyCollection).setBackgroundColor(ActorSDK.sharedActor().style.getMainBackgroundColor());

        res.findViewById(R.id.inviteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInvites();
            }
        });
        ((TextView) res.findViewById(R.id.no_contacts_text)).setTextColor(ActorSDK.sharedActor().style.getTextSecondaryAccentColor());
        ((TextView) res.findViewById(R.id.no_contacts_text)).setText(getString(R.string.contacts_empty_invite_hint).replace("{appName}", ActorSDK.sharedActor().getAppName()));
        ((TextView) res.findViewById(R.id.add_contact_hint_text)).setTextColor(ActorSDK.sharedActor().style.getTextSecondaryAccentColor());
        return res;
    }

    @Override
    public void onItemClicked(Contact contact) {
        getActivity().startActivity(Intents.openPrivateDialog(contact.getUid(), true, getActivity()));
    }

    @Override
    public boolean onItemLongClicked(final Contact contact) {
        new AlertDialog.Builder(getActivity())
                .setItems(new CharSequence[]{
                        getString(R.string.contacts_menu_remove).replace("{0}", contact.getName()),
                        getString(R.string.contacts_menu_edit),
                }, (dialog, which) -> {
                    if (which == 0) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage(getString(R.string.alert_remove_contact_text).replace("{0}", contact.getName()))
                                .setPositiveButton(R.string.alert_remove_contact_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        execute(messenger().removeContact(contact.getUid()), R.string.contacts_menu_remove_progress, new CommandCallback<Boolean>() {
                                            @Override
                                            public void onResult(Boolean res) {

                                            }

                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(R.string.dialog_cancel, null)
                                .show()
                                .setCanceledOnTouchOutside(true);
                    } else if (which == 1) {
                        startActivity(Intents.editUserName(contact.getUid(), getActivity()));
                    }
                })
                .show()
                .setCanceledOnTouchOutside(true);
        return true;
    }
}
